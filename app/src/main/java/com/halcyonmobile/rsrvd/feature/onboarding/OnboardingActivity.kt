package com.halcyonmobile.rsrvd.feature.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.api.*
import com.halcyonmobile.rsrvd.core.api.dto.ProfileDto
import com.halcyonmobile.rsrvd.core.api.dto.UserDto
import com.halcyonmobile.rsrvd.core.locator.Locator
import com.halcyonmobile.rsrvd.databinding.OnboardingActivityBinding
import com.halcyonmobile.rsrvd.feature.editprofile.ProfileUpdateHandler
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.halcyonmobile.rsrvd.feature.selectlocation.SelectLocationActivity
import retrofit2.Call
import retrofit2.Callback

class OnboardingActivity : AppCompatActivity() {
    private lateinit var viewModel: LocationViewModel
    private lateinit var binding: OnboardingActivityBinding

    private val locator: Locator = Locator(this) {
        it.let {
            viewModel.setLocation(
                Location(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    name = getString(R.string.current_location),
                    details = "current location"
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OnboardingActivityBinding.inflate(layoutInflater)
        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        setContentView(binding.root)

        locator.init()

        binding.dataMap = Interests.values().toMutableList()

        viewModel.getLocation().observe(this) { binding.setLocation(it) }

        binding.ready.setOnClickListener {
            viewModel.getLocation().value?.let {
                RetrofitManager.retrofit
                    .create(AppService::class.java)
                    .update(ProfileDto(location = it, interests = ArrayList(getInterests())))
                    .enqueue(ProfileUpdateHandler(applicationContext))
            }
//            startActivity(Intent(this, NEXTACTIVITY::class.java))
        }

        binding.locationSelector.setOnClickListener {
            startActivityForResult(
                Intent(this, SelectLocationActivity::class.java),
                SELECT_LOCATION_REQUEST_CODE,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.locationSelector, "search_trans").toBundle()
            )
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

        locator.onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun getInterests(): List<Interests> =
        binding.interestsGrid.children
            .filterIsInstance<InterestView>()
            .mapIndexed { index, view -> if (view.isChecked()) Interests.values()[index] else null }
            .filterNotNull()
            .toList()

    companion object {
        private const val SELECT_LOCATION_REQUEST_CODE = 1

        @JvmStatic
        @BindingAdapter("inflateData")
        fun inflateData(layout: FlexboxLayout, data: List<Interests>) {
            data.map { layout.addView(InterestView(layout.context).apply { setInterest(it.name) }) }
        }
    }
}