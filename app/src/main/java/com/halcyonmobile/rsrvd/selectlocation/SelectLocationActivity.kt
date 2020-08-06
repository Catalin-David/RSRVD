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
import com.google.android.libraries.places.api.net.*
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.databinding.SelectLocationBinding

class SelectLocationActivity : AppCompatActivity() {
    private lateinit var client: PlacesClient
    private lateinit var binding: SelectLocationBinding

    private val adapter: AutocompleteAdapter = AutocompleteAdapter { location ->
        // Only fetching details, if location is selected
        location.placeId?.let {
            client.fetchPlace(
                FetchPlaceRequest.newInstance(
                    it,
                    listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
                )
            )
                .addOnSuccessListener { response ->
                    if (response.place.name != null && response.place.address != null && response.place.latLng != null) {
                        val locationDetailed = Location(
                            name = response.place.name!!,
                            details = response.place.address!!,
                            latitude = response.place.latLng!!.latitude,
                            longitude = response.place.latLng!!.longitude,
                            placeId = response.place.id
                        )

                        setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_LOCATION, locationDetailed))
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
            Places.initialize(applicationContext, API_KEY)
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
        private const val API_KEY = "AIzaSyASUTwECBS--kaaBj71LFjps6kcGEh9Suo"
        private const val EXTRA_LOCATION = "location"
    }
}