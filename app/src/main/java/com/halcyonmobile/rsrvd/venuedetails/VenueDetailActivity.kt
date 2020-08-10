package com.halcyonmobile.rsrvd.venuedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.ActivityVenueDetailsBinding
import com.halcyonmobile.rsrvd.makereservation.MakeReservationActivity

class VenueDetailActivity : AppCompatActivity() {

    private lateinit var venueBinding: ActivityVenueDetailsBinding
    private lateinit var venueDetailAdapter: VenueDetailAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var viewModel: VenueDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val venueId : String
                = intent.getStringExtra(REQUEST_VENUE_ID)

        venueBinding = DataBindingUtil.setContentView(this, R.layout.activity_venue_details)
        viewModel = ViewModelProviders.of(this).get(VenueDetailViewModel::class.java)

        viewModel.getVenue(venueId) {
            Glide
                .with(this)
                .load(it.image)
                .placeholder(R.drawable.ic_baseline_cloud_download)
                .into(venueBinding.venueDetailsImageView)

            venueBinding.venueNameTextView.text = it.name
        }

        venueBinding.arrowBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        venueBinding.makeReservationButton.setOnClickListener {
            startActivity(Intent(this, MakeReservationActivity::class.java))
        }

        initTabLayout(venueId)
    }

    private fun initTabLayout(venueId: String) {
        val category = listOf("Details", "Reviews")

        venueDetailAdapter = VenueDetailAdapter(
            this,
            category,
            venueId)
        viewPager = venueBinding.viewPager
        viewPager.adapter = venueDetailAdapter

        TabLayoutMediator(venueBinding.tabLayout, viewPager) { tab, position ->
            tab.text = category[position]
        }.attach()
    }

    companion object {
        private const val REQUEST_VENUE_ID = "REQUEST_VENUE_ID"

        fun getStartIntent(context: Context, venueId: String) =
            Intent(context, VenueDetailActivity::class.java).putExtra(REQUEST_VENUE_ID, venueId)
    }

}
