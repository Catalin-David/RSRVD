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
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.venues.dto.VenueById
import com.halcyonmobile.rsrvd.databinding.FragmentInnerVenueDetailBinding
import com.halcyonmobile.rsrvd.venuedetails.VenueDetailViewModel
import com.schibstedspain.leku.LOCATION_ADDRESS
import com.schibstedspain.leku.LocationPickerActivity


class DetailInnerFragment: Fragment() {
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
            binding.venueLocationTextView.text = data?.getStringExtra(LOCATION_ADDRESS)
        }
    }

    private fun getDay(i: Int): String  =
        listOf(getString(R.string.mon),
            getString(R.string.tue),
            getString(R.string.wed),
            getString(R.string.thu),
            getString(R.string.fri),
            getString(R.string.sat),
            getString(R.string.sun))[i]

    private fun getHour(f: Float): String {
        val minutes: Int = (f % 1 * 100).toInt()

        return """${(f % 12).toInt()}:${if (minutes < 10) "0" else ""}$minutes ${if (f < 12) {getString(R.string.am)} else {getString(R.string.pm)}}""".trimMargin()
    }

    private fun getVenueProgram(venueById: VenueById): String {
        val listOpenHours = mutableListOf(
            venueById.open.dayZero,
            venueById.open.dayOne,
            venueById.open.dayTwo,
            venueById.open.dayThree,
            venueById.open.dayFour,
            venueById.open.dayFive,
            venueById.open.daySix)

        var start = venueById.open.dayZero.start
        var end = venueById.open.dayZero.end
        val listPositions: ArrayList<Int> = ArrayList()

        listOpenHours.map {
            if ( it.start != start || it.end != end ) {
                start = it.start
                end = it.end

                listPositions.add(listOpenHours.indexOf(it))
            }
        }

        var result = ""
        for (i in 0 until listPositions.size) {
            val day = getDay( listPositions[i] - 1 )
            val hStart = getHour(listOpenHours[ listPositions[i] ].start)
            val hEnd = getHour(listOpenHours[ listPositions[i] ].end)

            result +=
                if ( (i < listPositions.size - 1) && (listPositions[i] - listPositions[i + 1]) > 1)
                    "$day - ${getDay(listPositions[i])}, $hStart - $hEnd\n"
                else "$day, $hStart - $hEnd\n"
        }

        val index = if ( listPositions.isEmpty() ) 0 else listPositions.size - 1
        val day = if ( listPositions.isEmpty() ) getDay(index) else getDay(listPositions[index])
        val hStart = getHour(listOpenHours[index].start)
        val hEnd = getHour(listOpenHours[index].end)
        result += "$day - ${getDay(6)}, $hStart - ${hEnd}\n"

        return result
    }

    companion object {
        private const val PLACE_PICKER_REQUEST = 1
        private const val EXTRA_VENUE_ID = "EXTRA_VENUE_ID"

        fun newInstance(venueId: String) : DetailInnerFragment =
            DetailInnerFragment().apply {
                arguments = Bundle().apply { putString(EXTRA_VENUE_ID, venueId) }
            }
    }
}
