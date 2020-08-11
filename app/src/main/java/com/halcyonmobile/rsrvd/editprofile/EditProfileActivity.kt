package com.halcyonmobile.rsrvd.editprofile

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
import com.halcyonmobile.rsrvd.databinding.EditProfileActivityBinding
import com.halcyonmobile.rsrvd.onboarding.InterestView
import com.halcyonmobile.rsrvd.onboarding.LocationViewModel
import com.halcyonmobile.rsrvd.onboarding.RetrieveState
import com.halcyonmobile.rsrvd.selectlocation.LocationProvider
import com.halcyonmobile.rsrvd.selectlocation.SelectLocationActivity
import com.halcyonmobile.rsrvd.utils.showSnackbar

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: EditProfileActivityBinding
    private lateinit var viewModel: LocationViewModel

    private val locationProvider: LocationProvider = LocationProvider(this) {
        viewModel.setLocation(newLocation = it, save = true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        binding.locationViewModel = this.viewModel
        binding.lifecycleOwner = this

        viewModel.apply {
            loadSavedLocation()

            updateState.observe(this@EditProfileActivity) { binding.root.showSnackbar(if (it) "Updated" else "Failed") }

            errorMessage.observe(this@EditProfileActivity) { binding.root.showSnackbar(it) }

            interests.observe(this@EditProfileActivity) { markInterests() }

            retrieving.observe(this@EditProfileActivity) {
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

            close.setOnClickListener { finish() }

            mapsIcon.setOnClickListener { locationProvider.init() }

            locationSelector.setOnClickListener {
                startActivityForResult(
                    Intent(this@EditProfileActivity, SelectLocationActivity::class.java),
                    SELECT_LOCATION_REQUEST_CODE,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@EditProfileActivity,
                        binding.locationSelector,
                        "search_bar_transition"
                    ).toBundle()
                )
            }

            ready.setOnClickListener {
                if (viewModel.onReadyClick(getInterests())) {
                    finish()
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
            val position = Interests.values().indexOf(Interests.values().find { it.name == myInterest.name })
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
    }
}