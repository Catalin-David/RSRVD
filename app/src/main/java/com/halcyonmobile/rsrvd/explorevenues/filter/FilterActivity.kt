package com.halcyonmobile.rsrvd.explorevenues.filter

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
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.databinding.FilterActivityBinding
import com.halcyonmobile.rsrvd.onboarding.InterestView
import com.halcyonmobile.rsrvd.onboarding.LocationViewModel
import com.halcyonmobile.rsrvd.onboarding.RetrieveState
import com.halcyonmobile.rsrvd.selectlocation.LocationProvider
import com.halcyonmobile.rsrvd.selectlocation.SelectLocationActivity
import com.halcyonmobile.rsrvd.utils.showSnackbar

class FilterActivity : AppCompatActivity() {
    private lateinit var binding: FilterActivityBinding
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var filterViewModel: FilterViewModel

    private val locationProvider: LocationProvider = LocationProvider(this) {
        locationViewModel.setLocation(it)
        filterViewModel.setLocation(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        filterViewModel = ViewModelProviders.of(this).get(FilterViewModel::class.java)

        binding.apply {
            dataMap = Interests.values().toMutableList()

            viewModel = locationViewModel
            lifecycleOwner = this@FilterActivity

            locationSelector.setOnClickListener {
                startActivityForResult(
                    Intent(this@FilterActivity, SelectLocationActivity::class.java),
                    SELECT_LOCATION_REQUEST_CODE,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@FilterActivity,
                        binding.locationSelector,
                        "search_bar_transition"
                    ).toBundle()
                )
            }

            mapsIcon.setOnClickListener {
                locationProvider.init()
            }

            cancel.setOnClickListener { finish() }

            ready.setOnClickListener {
                if (!filterViewModel.isReady()) {
                    binding.root.showSnackbar(getString(R.string.no_filters))
                } else {
                    setResult(
                        Activity.RESULT_OK, Intent().putExtra(
                            FILTERS, Filters(
                                activities = if (getActivities().isNotEmpty()) getActivities() else null,
                                location = filterViewModel.location.value
                            )
                        )
                    )

                    finish()
                }
            }
        }

        locationViewModel.apply {
            updateState.observe(this@FilterActivity) {
                binding.root.showSnackbar(if (it) getString(R.string.updated) else getString(R.string.failed))
            }

            errorMessage.observe(this@FilterActivity) {
                binding.root.showSnackbar(it)
            }

            location.observe(this@FilterActivity) {
                filterViewModel.setLocation(it)
            }

            retrieving.observe(this@FilterActivity) {
                when (it) {
                    RetrieveState.PRE -> binding.mapsText.text = getString(R.string.loading)
                    RetrieveState.POST -> {
                        binding.mapsText.text = getString(R.string.pick_location)
                        locationProvider.init()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_LOCATION_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            data.getParcelableExtra<Location>(SelectLocationActivity.LOCATION)?.let {
                locationViewModel.setLocation(it)
                filterViewModel.setLocation(it)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        locationProvider.onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun getActivities(): List<Interests> = binding.activities.children
        .filterIsInstance<InterestView>()
        .mapIndexed { index, view -> if (view.isChecked()) Interests.values()[index] else null }
        .filterNotNull()
        .toList()

    companion object {
        const val SELECT_LOCATION_REQUEST_CODE = 1
        const val FILTERS = "filters"
    }
}