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
import com.halcyonmobile.rsrvd.core.shared.LocationProvider
import com.halcyonmobile.rsrvd.databinding.FilterActivityBinding
import com.halcyonmobile.rsrvd.onboarding.InterestView
import com.halcyonmobile.rsrvd.onboarding.Interests
import com.halcyonmobile.rsrvd.onboarding.LocationViewModel
import com.halcyonmobile.rsrvd.onboarding.RetrieveState
import com.halcyonmobile.rsrvd.selectlocation.Location
import com.halcyonmobile.rsrvd.selectlocation.SelectLocationActivity
import com.halcyonmobile.rsrvd.utils.showSnackbar

class FilterActivity : AppCompatActivity() {
    private lateinit var binding: FilterActivityBinding
    private lateinit var viewModel: LocationViewModel

    private val locationProvider: LocationProvider = LocationProvider(this) {
        viewModel.setLocation(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            dataMap = Interests.values().toMutableList()

            locationSelector.setOnClickListener {
                startActivityForResult(
                    Intent(this@FilterActivity, SelectLocationActivity::class.java),
                    SELECT_LOCATION_REQUEST_CODE,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@FilterActivity,
                        binding.locationSelector,
                        "search_trans"
                    ).toBundle()
                )
            }

            cancel.setOnClickListener { finish() }

            ready.setOnClickListener {
//            setResult()
                finish()
            }
        }

        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java).apply {
            location.observe(this@FilterActivity) {
                binding.location = it
            }

            updateState.observe(this@FilterActivity) {
                binding.root.showSnackbar(if (it) "Updated" else "Failed").show()
            }

            errorMessage.observe(this@FilterActivity) {
                binding.root.showSnackbar(it).show()
            }

            interests.observe(this@FilterActivity) {
                markInterests()
            }

            retrieving.observe(this@FilterActivity) {
                when (it) {
                    RetrieveState.PRE -> binding.mapsText.text = getString(R.string.loading)
                    RetrieveState.POST ->
                        if (viewModel.location.value != null) binding.mapsText.text = viewModel.location.value!!.name
                        else {
                            binding.mapsText.text = getString(R.string.pick_location)
                            locationProvider.init()
                        }
                }
            }
        }

        locationProvider.init()
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

    private fun markInterests() {
        viewModel.interests.value?.map { myInterest ->
            val position = Interests.values().indexOf(Interests.values().find { it.name == myInterest.name })
            (binding.activities.children.toList()[position] as InterestView).setChecked(true)
        }
    }

    private fun getInterests(): List<Interests> = binding.activities.children
        .filterIsInstance<InterestView>()
        .mapIndexed { index, view -> if (view.isChecked()) Interests.values()[index] else null }
        .filterNotNull()
        .toList()

    companion object {
        const val SELECT_LOCATION_REQUEST_CODE = 1
    }
}