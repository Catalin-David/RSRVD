package com.halcyonmobile.rsrvd.feature.onboarding

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.google.android.flexbox.FlexboxLayout
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.databinding.OnboardingActivityBinding
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.halcyonmobile.rsrvd.feature.selectlocation.SelectLocationActivity

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
             startActivity(Intent(this, MainActivity::class.java))
        }

        binding.locationSelector.setOnClickListener {
            // Shared item transition
            startActivityForResult(
                Intent(this, SelectLocationActivity::class.java),
                SELECT_LOCATION_REQUEST_CODE,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.locationSelector, "search_trans").toBundle()
            )

            // No transition
//            startActivityForResult(Intent(this, SelectLocationActivity::class.java), SELECT_LOCATION_REQUEST_CODE)
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
                        viewModel.setLocation(
                            Location(
                                latitude = locationResult.locations[locationResult.locations.size - 1].latitude,
                                longitude = locationResult.locations[locationResult.locations.size - 1].longitude,
                                name = "Current location"
                            )
                        )
                    }
                }
            }, Looper.getMainLooper())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_LOCATION_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            data.getParcelableExtra<Location>("location")?.let { viewModel.setLocation(it) }
        }
    }

    private fun getInterests(): List<Interests> =
        binding.interestsGrid.children
            .filterIsInstance<InterestView>()
            .mapIndexed { index, view -> if (view.isChecked()) Interests.values()[index] else null }
            .filterNotNull()
            .toList()

    companion object {
        private const val SELECT_LOCATION_REQUEST_CODE = 1
        private const val LOCATION_PERMISSION_REQUEST_CODE = 2

        @JvmStatic
        @BindingAdapter("inflateData")
        fun inflateData(layout: FlexboxLayout, data: List<Interests>) {
            data.map { layout.addView(InterestView(layout.context).apply { setInterest(it.name) }) }
        }
    }
}