package com.halcyonmobile.rsrvd.selectlocation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.halcyonmobile.rsrvd.core.shared.Location
import java.util.*

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
        LocationServices.getFusedLocationProviderClient(activity)
            .requestLocationUpdates(LocationRequest().apply {
                interval = 10000
                fastestInterval = 3000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)

                    LocationServices.getFusedLocationProviderClient(activity).removeLocationUpdates(this)

                    if (locationResult != null && locationResult.locations.isNotEmpty()) {
                        val address = Geocoder(activity, Locale.getDefault()).getFromLocation(
                            locationResult.lastLocation.latitude,
                            locationResult.lastLocation.longitude,
                            1
                        )[0]
                        callback(
                            Location(
                                name = address.locality,
                                details = address.getAddressLine(0),
                                latitude = locationResult.lastLocation.latitude,
                                longitude = locationResult.lastLocation.longitude
                            )
                        )
                    }
                }
            }, Looper.getMainLooper())
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 2
    }
}