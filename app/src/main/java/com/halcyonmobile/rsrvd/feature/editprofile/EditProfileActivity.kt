package com.halcyonmobile.rsrvd.feature.editprofile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.google.android.flexbox.FlexboxLayout
import com.halcyonmobile.rsrvd.core.locator.Locator
import com.halcyonmobile.rsrvd.databinding.EditProfileActivityBinding
import com.halcyonmobile.rsrvd.feature.onboarding.*
import com.halcyonmobile.rsrvd.feature.selectlocation.Location
import com.halcyonmobile.rsrvd.feature.selectlocation.SelectLocationActivity

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: EditProfileActivityBinding
    private lateinit var viewModel: LocationViewModel

    private val locator: Locator = Locator(this) {
        it?.let {
            viewModel.setLocation(
                Location(
                    latitude = it.locations[it.locations.size - 1].latitude,
                    longitude = it.locations[it.locations.size - 1].longitude,
                    name = "Current location"
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

        binding.ready.setOnClickListener {
            val data = OnboardingData(viewModel.getLocation().value, getInterests())
            setResult(Activity.RESULT_OK, Intent().putExtra("location", data))

            // Shared item transition after closing keyboard
            this.currentFocus?.let { view ->
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
            }
            supportFinishAfterTransition()

            // No transition
//            finish()
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

        binding.close.setOnClickListener { finish() }
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