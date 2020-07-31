package com.halcyonmobile.rsrvd.core.shared

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.halcyonmobile.rsrvd.selectlocation.Location
import com.halcyonmobile.rsrvd.selectlocation.SelectLocationActivity

class LocationProvider(private val activity: Activity, private val callback: (Location) -> Unit) {
    fun init() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getLocation()
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (!Places.isInitialized()) {
            Places.initialize(activity, SelectLocationActivity.apikey)
        }

        Places.createClient(activity)
            .findCurrentPlace(FindCurrentPlaceRequest.builder(listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ID, Place.Field.LAT_LNG)).build())
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val place = task.result?.placeLikelihoods?.get(0)?.place
                    callback(
                        Location(
                            placeId = place?.id,
                            name = place?.name!!,
                            details = place.address!!,
                            latitude = place.latLng!!.latitude,
                            longitude = place.latLng!!.longitude
                        )
                    )
                }
            }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 2
    }
}