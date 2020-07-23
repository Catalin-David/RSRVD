package com.halcyonmobile.rsrvd.feature.onboarding

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.flexbox.FlexboxLayout
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.halcyonmobile.rsrvd.feature.selectlocation.SelectLocationActivity
import java.util.*
import kotlin.collections.ArrayList

class OnboardingActivity : AppCompatActivity() {
    private val selectLocationRequestCode = 1
    private val locationPermissionRequestCode = 2

    private var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding)

        loadInterests()

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionRequestCode)
        } else {
            getLocation()
        }
    }

    private fun loadInterests() {
        Interests.values().map {
            findViewById<FlexboxLayout>(R.id.interests_grid).addView(InterestView(applicationContext).apply { setInterest(it.toString()) })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == locationPermissionRequestCode && grantResults.isNotEmpty()) {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(LocationRequest().apply {
                interval = 10000
                fastestInterval = 3000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)

                    LocationServices.getFusedLocationProviderClient(applicationContext).removeLocationUpdates(this)

                    if (locationResult != null && locationResult.locations.isNotEmpty()) {
                        val latestIndex = locationResult.locations.size - 1
                        val latitude = locationResult.locations[latestIndex].latitude
                        val longitude = locationResult.locations[latestIndex].longitude

                        location = Location(UUID.randomUUID(), latitude, longitude, "Current location", "", null)
                        findViewById<TextView>(R.id.maps_text).text = location.toString()
                    }
                }
            }, Looper.getMainLooper())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == selectLocationRequestCode && resultCode == Activity.RESULT_OK && data != null) {
            location = data.getParcelableExtra("location")
            findViewById<TextView>(R.id.maps_text).text = location.toString()
        }
    }

    fun selectLocation(view: View) {
        startActivityForResult(Intent(this, SelectLocationActivity::class.java), selectLocationRequestCode)
    }

    fun ready(view: View) {
        val data = OnboardingData(location, getInterests())
//        startActivity(Intent(this, NEXTACTIVITY::class.java).putExtra("data", data))
    }

    private fun getInterests(): List<Interests> {
        val interests = ArrayList<Interests>()
        val interestsGrid = findViewById<FlexboxLayout>(R.id.interests_grid)
        for (i in 0 until interestsGrid.childCount) {
            if ((interestsGrid.getChildAt(i) as InterestView).isChecked()) {
                interests.add(Interests.values()[i])
            }
        }
        return interests
    }
}