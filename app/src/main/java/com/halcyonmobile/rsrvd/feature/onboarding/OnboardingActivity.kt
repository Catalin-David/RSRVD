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
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.google.android.flexbox.FlexboxLayout
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.halcyonmobile.rsrvd.databinding.OnboardingActivityBinding
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.halcyonmobile.rsrvd.feature.selectlocation.SelectLocationActivity
import java.util.*
import kotlin.collections.ArrayList

class OnboardingActivity : AppCompatActivity() {
    private lateinit var viewModel: OnboardingViewModel
    private lateinit var binding: OnboardingActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OnboardingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(OnboardingViewModel::class.java)

        binding.dataMap = Interests.values().toMutableList()

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            getLocation()
        }

        viewModel.getLocation().observe(this) { binding.setLocation(it) }

        binding.ready.setOnClickListener {
            val data = OnboardingData(viewModel.getLocation().value, getInterests())
            // startActivity(Intent(this, NEXTACTIVITY::class.java).putExtra("data", data))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
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
                            latitude = locationResult.locations[locationResult.locations.size - 1].latitude,
                            longitude = locationResult.locations[locationResult.locations.size - 1].longitude,
                            name = "Current location")))
                    }
                }
            }, Looper.getMainLooper())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_LOCATION_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val location: Location? = data.getParcelableExtra("location")
            location?.let { viewModel.setLocation(MutableLiveData(it)) }
        }
    }

    fun selectLocation(view: View) {
        startActivityForResult(Intent(this, SelectLocationActivity::class.java), SELECT_LOCATION_REQUEST_CODE)
    }

    private fun getInterests(): List<Interests> =
        binding.interestsGrid.children
            .filterIsInstance<InterestView>()
            .mapIndexed { index, view -> if (view.isChecked()) Interests.values()[index] else null }
            .filterNotNull()
            .toList()

    companion object {
        private const val TAG = "OnboardingActivity"

        private const val SELECT_LOCATION_REQUEST_CODE = 1
        private const val LOCATION_PERMISSION_REQUEST_CODE = 2

        @JvmStatic
        @BindingAdapter("inflateData")
        fun inflateData(layout: FlexboxLayout, data: List<Interests>) {
            for (entry in data) {
                layout.addView(InterestView(layout.context).apply { setInterest(entry.name) })
            }
        }
    }
}