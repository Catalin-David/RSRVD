package com.halcyonmobile.rsrvd.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.children
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.selectlocation.LocationProvider
import com.halcyonmobile.rsrvd.databinding.ActivityOnboardingBinding
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.selectlocation.SelectLocationActivity
import com.halcyonmobile.rsrvd.utils.showSnackbar

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewModel: LocationViewModel

    private val locationProvider: LocationProvider =
        LocationProvider(this) {
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
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java).apply {
            location.observe(this@OnboardingActivity) {
                binding.setLocation(it)
            }

            updateState.observe(this@OnboardingActivity) {
                binding.root.showSnackbar(if (it) "Updated" else "Failed").show()
            }

            errorMessage.observe(this@OnboardingActivity) {
                binding.root.showSnackbar(it).show()
            }
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
                viewModel.onReadyClick(getInterests())
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