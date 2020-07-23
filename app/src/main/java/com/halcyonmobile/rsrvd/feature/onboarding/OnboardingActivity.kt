package com.halcyonmobile.rsrvd.feature.onboarding

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.halcyonmobile.rsrvd.databinding.OnboardingBinding
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.halcyonmobile.rsrvd.feature.selectlocation.SelectLocationActivity
import java.util.*
import kotlin.collections.ArrayList

class OnboardingActivity : AppCompatActivity() {
    private val selectLocationRequestCode = 1
    private val locationPermissionRequestCode = 2

    private lateinit var viewModel: OnboardingViewModel
    private lateinit var binding: OnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(OnboardingViewModel::class.java)

        binding.dataMap = Interests.values().toMutableList()

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionRequestCode)
        } else {
            getLocation()
        }

        viewModel.getLocation().observe(this, Observer<Location> { binding.setLocation(it) })
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
                        viewModel.setLocation(MutableLiveData(Location(
                            UUID.randomUUID(),
                            locationResult.locations[locationResult.locations.size - 1].latitude,
                            locationResult.locations[locationResult.locations.size - 1].longitude,
                            "Current location")))
                    }
                }
            }, Looper.getMainLooper())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == selectLocationRequestCode && resultCode == Activity.RESULT_OK && data != null) {
            val location: Location? = data.getParcelableExtra("location")
            location?.let { viewModel.setLocation(MutableLiveData(it)) }
        }
    }

    fun selectLocation(view: View) {
        startActivityForResult(Intent(this, SelectLocationActivity::class.java), selectLocationRequestCode)
    }

    fun ready(view: View) {
        val data = OnboardingData(viewModel.getLocation().value, getInterests())
//        startActivity(Intent(this, NEXTACTIVITY::class.java).putExtra("data", data))
    }

    private fun getInterests(): List<Interests> {
        val interests = ArrayList<Interests>()
        for (i in 0 until binding.interestsGrid.childCount) {
            if ((binding.interestsGrid.getChildAt(i) as InterestView).isChecked()) {
                interests.add(Interests.values()[i])
            }
        }
        return interests
    }
}