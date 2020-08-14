package com.halcyonmobile.rsrvd.venuedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.ActivityVenueDetailsBinding
import com.halcyonmobile.rsrvd.makereservation.activity.MakeReservationActivity

class VenueDetailActivity : AppCompatActivity() {

    private lateinit var venueBinding: ActivityVenueDetailsBinding
    private lateinit var venueDetailAdapter: VenueDetailAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var viewModel: VenueDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val venueId: String? = intent.getStringExtra(REQUEST_VENUE_ID)

        venueBinding = DataBindingUtil.setContentView(this, R.layout.activity_venue_details)
        viewModel = ViewModelProviders.of(this).get(VenueDetailViewModel::class.java)

        venueBinding.lifecycleOwner = this
        venueBinding.viewModel = viewModel

        venueId?.let {
            viewModel.getVenue(it)
            initTabLayout(it)
        }

        venueBinding.arrowBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
//            startActivity(Intent(this, MainActivity::class.java), ExploreFragment.getTransition(this, venueBinding.venueDetailsImageView))
        }

        venueBinding.makeReservationButton.setOnClickListener {
            startActivity(venueId?.let { it1 -> MakeReservationActivity.getStartIntent(this, it1) })
        }
    }

    private fun initTabLayout(venueId: String) {
        val category = listOf("Details", "Reviews")

        venueDetailAdapter = VenueDetailAdapter(this, category, venueId)
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
