package com.halcyonmobile.rsrvd.selectlocation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.model.Location
import com.halcyonmobile.rsrvd.databinding.SelectLocationBinding

class SelectLocationActivity : AppCompatActivity() {
    private lateinit var client: PlacesClient
    private lateinit var binding: SelectLocationBinding

    private val adapter: AutocompleteAdapter = AutocompleteAdapter { location ->
        // Only fetching details, if location is selected
        location.placeId?.let {
            client.fetchPlace(
                FetchPlaceRequest.newInstance(
                    location.placeId as String,
                    listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
                )
            )
                .addOnSuccessListener {
                    if (it.place.name != null && it.place.address != null && it.place.latLng != null) {
                        val locationDetailed = Location(
                            name = it.place.name!!,
                            details = it.place.address!!,
                            latitude = it.place.latLng!!.latitude,
                            longitude = it.place.latLng!!.longitude,
                            placeId = it.place.id
                        )

                        setResult(Activity.RESULT_OK, Intent().putExtra("location", locationDetailed))
                    }
                }
                .addOnFailureListener { println("SOMETHING WENT WRONG ___ DETAILS") }
        }

        // Shared item transition after closing keyboard
        this.currentFocus?.let { view ->
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
        }
        supportFinishAfterTransition()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apikey)
        }

        client = Places.createClient(this)

        binding.searchText.apply {
            addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if (s.toString().isNotEmpty()) {
                            setSuggestions(s)
                        }
                    }
                }
            )
            requestFocus()
        }

        binding.clear.setOnClickListener {
            binding.searchText.text.clear()
            adapter.submitList(listOf())
            binding.emptyPlaceholder.visibility = View.VISIBLE
        }

        binding.locationList.apply {
            layoutManager = LinearLayoutManager(this@SelectLocationActivity)
            this.adapter = this@SelectLocationActivity.adapter
            addItemDecoration(Divider(context))
        }

        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                // Shared Item transition after closing keyboard
                this.currentFocus?.let {
                    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(it.windowToken, 0)
                }
                supportFinishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setSuggestions(s: CharSequence?) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(s.toString())
            .build()

        client.findAutocompletePredictions(request).addOnSuccessListener { response ->
            val predictions = response.autocompletePredictions.map {
                Location(
                    name = it.getPrimaryText(null).toString(),
                    details = it.getFullText(null).toString(),
                    placeId = it.placeId
                )
            }

            adapter.submitList(predictions)

            binding.emptyPlaceholder.visibility = if (predictions.isNotEmpty()) View.INVISIBLE else View.VISIBLE
        }.addOnFailureListener {
            Log.d("autocompletion error", "error")
        }
    }

    companion object {
        private const val apikey = "AIzaSyASUTwECBS--kaaBj71LFjps6kcGEh9Suo"
    }
}