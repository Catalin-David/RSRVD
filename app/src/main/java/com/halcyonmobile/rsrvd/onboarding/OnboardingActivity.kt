package com.halcyonmobile.rsrvd.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.children
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.selectlocation.LocationProvider
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.databinding.ActivityOnboardingBinding
import com.halcyonmobile.rsrvd.selectlocation.SelectLocationActivity
import com.halcyonmobile.rsrvd.utils.showSnackbar

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewModel: LocationViewModel

    private val locationProvider: LocationProvider = LocationProvider(this) {
        viewModel.setLocation(newLocation = it, save = true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        binding.locationViewModel = this.viewModel
        binding.lifecycleOwner = this

        viewModel.apply {
            loadSavedLocation()

            updateState.observe(this@OnboardingActivity) { binding.root.showSnackbar(if (it) getString(R.string.updated) else getString(R.string.failed)) }
            errorMessage.observe(this@OnboardingActivity) { binding.root.showSnackbar(it) }

            interests.observe(this@OnboardingActivity) { markInterests() }

            retrieving.observe(this@OnboardingActivity) {
                when (it) {
                    RetrieveState.PRE -> binding.mapsText.text = getString(R.string.loading)
                    RetrieveState.POST ->
                        if (viewModel.location.value != null) {
                            binding.mapsText.text = viewModel.location.value!!.name
                        } else {
                            binding.mapsText.text = getString(R.string.pick_location)
                            locationProvider.init()
                        }
                }
            }
        }

        binding.apply {
            dataMap = Interests.values().toMutableList()

            mapsIcon.setOnClickListener { locationProvider.init() }

            locationSelector.setOnClickListener {
                startActivityForResult(
                    getStartIntent(this@OnboardingActivity, locationViewModel?.location?.value?.name),
                    SELECT_LOCATION_REQUEST_CODE,
                    getTransition(this@OnboardingActivity, binding.locationSelector)
                )
            }

            ready.setOnClickListener {
                if (viewModel.onReadyClick(getInterests())) {
                    startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_LOCATION_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            data.getParcelableExtra<Location>(SelectLocationActivity.LOCATION)?.let {
                viewModel.setLocation(newLocation = it, save = true)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        locationProvider.onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun markInterests() {
        viewModel.interests.value?.map { myInterest ->
            val position = Interests.values().indexOf(
                Interests.values().find { it.name == myInterest.name })
            (binding.interestsGrid.children.toList()[position] as InterestView).setChecked(true)
        }
    }

    private fun getInterests(): List<Interests> = binding.interestsGrid.children
        .filterIsInstance<InterestView>()
        .mapIndexed { index, view -> if (view.isChecked()) Interests.values()[index] else null }
        .filterNotNull()
        .toList()

    companion object {
        private const val SELECT_LOCATION_REQUEST_CODE = 1

        private const val TRANSITION = "search_bar_transition"

        fun getStartIntent(activity: Activity, locationName: String?) = Intent(activity, SelectLocationActivity::class.java).putExtra(
            SelectLocationActivity.EXTRA_STRING,
            locationName
        )

        fun getTransition(activity: Activity, element: View) = ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity,
            element,
            TRANSITION
        ).toBundle()
    }
}