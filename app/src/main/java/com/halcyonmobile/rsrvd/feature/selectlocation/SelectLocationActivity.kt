package com.halcyonmobile.rsrvd.feature.selectlocation

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.halcyonmobile.rsrvd.databinding.SelectLocationBinding
import java.util.*
import kotlin.collections.ArrayList

class SelectLocationActivity : AppCompatActivity() {
    private val adapter: AutocompleteAdapter = AutocompleteAdapter {
        setResult(Activity.RESULT_OK, Intent().putExtra("location", it))
        finish()
    }

    private lateinit var client: PlacesClient
    private lateinit var binding: SelectLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apikey)
        }

        client = Places.createClient(this)

        binding.searchText.addTextChangedListener(
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

        binding.searchText.requestFocus()

        binding.locationList.apply {
            layoutManager = LinearLayoutManager(this@SelectLocationActivity)
            this.adapter = this@SelectLocationActivity.adapter
            addItemDecoration(Divider(context))
        }
    }

    fun setSuggestions(s: CharSequence?) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(s.toString())
            .build()

        val predictions = ArrayList<Location>()

        client.findAutocompletePredictions(request).addOnSuccessListener { response ->
            for (prediction in response.autocompletePredictions) {
                predictions.add(Location(
                    name = prediction.getPrimaryText(null).toString(),
                    details = prediction.getFullText(null).toString(),
                    placeId = prediction.placeId))
            }
            adapter.submitList(predictions)

            binding.emptyPlaceholder.visibility = if (predictions.size > 0) View.INVISIBLE else View.VISIBLE
        }.addOnFailureListener {
            Log.d("autocompletion error", "error")
        }
    }

    fun clear(view: View) {
        binding.searchText.text.clear()
        adapter.submitList(listOf())
        binding.emptyPlaceholder.visibility = View.VISIBLE
    }

    companion object {
        private val apikey = "AIzaSyASUTwECBS--kaaBj71LFjps6kcGEh9Suo"

        private const val TAG = "SelectLocationActivity"
    }
}