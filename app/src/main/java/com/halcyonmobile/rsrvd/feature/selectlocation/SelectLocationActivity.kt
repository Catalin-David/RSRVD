package com.halcyonmobile.rsrvd.feature.selectlocation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.halcyonmobile.rsrvd.R
import java.util.*
import kotlin.collections.ArrayList


class SelectLocationActivity : AppCompatActivity() {
    private val adapter: AutocompleteAdapter = AutocompleteAdapter {
        setResult(Activity.RESULT_OK, Intent().putExtra("location", it))
        finish()
    }

    private lateinit var client: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_location)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apikey)
        }

        client = Places.createClient(this)

        findViewById<EditText>(R.id.search_text).addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setSuggestions(s)
                }
            }
        )

        findViewById<RecyclerView>(R.id.location_list).apply {
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
                predictions.add(Location(UUID.randomUUID(), 0.0, 0.0, prediction.getPrimaryText(null).toString(), prediction.getFullText(null).toString(), prediction.placeId))
            }
            adapter.submitList(predictions)
        }.addOnFailureListener {
            Log.d("autocompletion error", "error")
        }
    }

    fun clear(view: View) {
        findViewById<EditText>(R.id.search_text).text.clear()
    }

    companion object {
        private val apikey = "AIzaSyASUTwECBS--kaaBj71LFjps6kcGEh9Suo"
    }
}