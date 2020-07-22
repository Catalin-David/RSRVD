package com.halcyonmobile.rsrvd.feature.selectlocation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.halcyonmobile.rsrvd.R
import java.util.*


class SelectLocationActivity : AppCompatActivity() {
    private val apikey = "AIzaSyASUTwECBS--kaaBj71LFjps6kcGEh9Suo"

//    private val adapter: AutocompleteAdapter = AutocompleteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_location)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apikey)
        }

        Places.createClient(this)

//        findViewById<EditText>(R.id.search_text).addTextChangedListener(
//            object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                }
//
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    adapter.submitList(getSuggestions(s))
//                }
//            }
//        )

//        findViewById<RecyclerView>(R.id.location_list).apply {
//            setHasFixedSize(false)
//            layoutManager = LinearLayoutManager(this@SelectLocationActivity)
//            this.adapter = this@SelectLocationActivity.adapter
//        }

        (supportFragmentManager.findFragmentById(R.id.location_list) as AutocompleteSupportFragment).apply {
            setPlaceFields(listOf(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME))
            setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    if (place.name != null) {
                        setResult(Activity.RESULT_OK, Intent().putExtra("location", Location(UUID.randomUUID(), place.latLng!!.latitude, place.latLng!!.longitude, place.name!!)))
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Autocompletion failed", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onError(place: Status) {
                    Toast.makeText(applicationContext, "Autocompletion failed", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    fun getSuggestions(s: CharSequence?): MutableList<Location>? {
        return null
    }

    fun clear(view: View) {
        findViewById<EditText>(R.id.search_text).text.clear()
    }
}