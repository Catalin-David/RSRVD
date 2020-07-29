package com.halcyonmobile.rsrvd.feature.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.children
import androidx.lifecycle.*
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.api.*
import com.halcyonmobile.rsrvd.core.api.dto.ProfileDto
import com.halcyonmobile.rsrvd.core.shared.LocationProvider
import com.halcyonmobile.rsrvd.databinding.OnboardingActivityBinding
import com.halcyonmobile.rsrvd.feature.editprofile.ProfileUpdateHandler
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.halcyonmobile.rsrvd.feature.selectlocation.SelectLocationActivity
import com.iuliamariabirsan.core.repository.UserRepository

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: OnboardingActivityBinding
    private lateinit var viewModel: LocationViewModel

    private val locationProvider: LocationProvider = LocationProvider(this) {
        viewModel.setLocation(
            Location(
                name = getString(R.string.current_location),
                details = "current location",
                latitude = it.latitude,
                longitude = it.longitude
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OnboardingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java).apply {
            getLocation().observe(this@OnboardingActivity) { binding.setLocation(it) }
        }

        locationProvider.init()

        binding.apply {
            dataMap = Interests.values().toMutableList()

            locationSelector.setOnClickListener {
                startActivityForResult(
                    Intent(this@OnboardingActivity, SelectLocationActivity::class.java),
                    SELECT_LOCATION_REQUEST_CODE,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@OnboardingActivity,
                        binding.locationSelector,
                        "search_trans"
                    ).toBundle()
                )
            }

            ready.setOnClickListener {
                viewModel.getLocation().value?.let {
                    RetrofitManager.retrofit
                        .create(MeApi::class.java)
                        .update(ProfileDto(location = it, interests = ArrayList(getInterests()), name = UserRepository.getName()))
                        .enqueue(ProfileUpdateHandler(binding.root))
                }

                // startActivity(Intent(this@OnboardingActivity, NEXTACTIVITY::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_LOCATION_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            data.getParcelableExtra<Location>("location")?.let { viewModel.setLocation(it) }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        locationProvider.onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun getInterests(): List<Interests> = binding.interestsGrid.children
        .filterIsInstance<InterestView>()
        .mapIndexed { index, view -> if (view.isChecked()) Interests.values()[index] else null }
        .filterNotNull()
        .toList()

    companion object {
        private const val SELECT_LOCATION_REQUEST_CODE = 1
    }
}