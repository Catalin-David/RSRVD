package com.halcyonmobile.rsrvd.feature.editprofile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.api.*
import com.halcyonmobile.rsrvd.core.api.dto.ProfileDto
import com.halcyonmobile.rsrvd.core.api.dto.UserDto
import com.halcyonmobile.rsrvd.core.locator.Locator
import com.halcyonmobile.rsrvd.databinding.EditProfileActivityBinding
import com.halcyonmobile.rsrvd.feature.onboarding.*
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.halcyonmobile.rsrvd.feature.selectlocation.SelectLocationActivity
import retrofit2.Call
import retrofit2.Callback

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: EditProfileActivityBinding
    private lateinit var viewModel: LocationViewModel

    private val locator: Locator = Locator(this) {
        it.let {
            viewModel.setLocation(
                Location(
                    name = getString(R.string.current_location),
                    details = "current location",
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditProfileActivityBinding.inflate(layoutInflater)
        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        setContentView(binding.root)

        locator.init()

        binding.dataMap = Interests.values().toMutableList()

        viewModel.getLocation().observe(this) { binding.setLocation(it) }

        binding.close.setOnClickListener { finish() }

        binding.ready.setOnClickListener {
            viewModel.getLocation().value?.let {
                RetrofitManager.retrofit
                    .create(AppService::class.java)
                    .update(ProfileDto(location = it, interests = ArrayList(getInterests())))
                    .enqueue(ProfileUpdateHandler(binding.root))
            }

            // finish()
        }

        binding.locationSelector.setOnClickListener {
            // Shared item transition
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