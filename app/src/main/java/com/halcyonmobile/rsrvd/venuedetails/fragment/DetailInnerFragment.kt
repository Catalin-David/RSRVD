@file:Suppress("DEPRECATION")

package com.halcyonmobile.rsrvd.venuedetails.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.Places
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.FragmentInnerVenueDetailBinding
import com.halcyonmobile.rsrvd.venuedetails.VenueDetailViewModel
import com.schibstedspain.leku.LOCATION_ADDRESS
import com.schibstedspain.leku.LocationPickerActivity


class DetailInnerFragment : Fragment() {
    private var mGoogleApiClient: GoogleApiClient? = null

    private lateinit var binding: FragmentInnerVenueDetailBinding
    private lateinit var viewModel: VenueDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInnerVenueDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VenueDetailViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val venueId = this.arguments?.getString(EXTRA_VENUE_ID)

        //initializing the data for the get venue by id request
        venueId?.let {
            viewModel.getVenue(it)
        }

        //for the place picker
        mGoogleApiClient = activity?.let {
            GoogleApiClient.Builder(it)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(it, null)
                .build()
        }

        //setting the on click listeners and initializing the flexbox layouts
        binding.apply {
            youtubeVideoContainer.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.venue_tour_link))))
            }

            containerDirections.setOnClickListener {
                val locationPickerIntent = activity?.applicationContext?.let { it1 ->
                    LocationPickerActivity.Builder()
                        .withGooglePlacesApiKey(getString(R.string.google_api_key))
                        .build(it1)
                }

                startActivityForResult(locationPickerIntent, PLACE_PICKER_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == Activity.RESULT_OK) {
//            binding.venueLocationTextView.text = data?.getStringExtra(LOCATION_ADDRESS)
        }
    }

    companion object {
        private const val PLACE_PICKER_REQUEST = 1
        private const val EXTRA_VENUE_ID = "EXTRA_VENUE_ID"

        fun newInstance(venueId: String): DetailInnerFragment =
            DetailInnerFragment().apply {
                arguments = Bundle().apply { putString(EXTRA_VENUE_ID, venueId) }
            }
    }
}
