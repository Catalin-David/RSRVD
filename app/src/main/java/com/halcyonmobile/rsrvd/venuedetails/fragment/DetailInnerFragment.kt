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
import com.halcyonmobile.rsrvd.core.venues.dto.DailyOpenHours
import com.halcyonmobile.rsrvd.core.venues.dto.VenueById
import com.halcyonmobile.rsrvd.databinding.FragmentInnerVenueDetailBinding
import com.halcyonmobile.rsrvd.venuedetails.VenueDetailViewModel
import com.schibstedspain.leku.LOCATION_ADDRESS
import com.schibstedspain.leku.LocationPickerActivity


class DetailInnerFragment(private val venueId: String): Fragment() {

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
        viewModel = ViewModelProviders.of(this).get(VenueDetailViewModel::class.java)

        //initializing the data for the get venue by id request
        viewModel.getVenue(venueId, callback = {
            binding.venueWeeklyProgramTextView.text = getVenueProgram(it)
            binding.venueDescription.text = it.description
            binding.venueLocationTextView.text = getString(R.string.location_description, it.location.name, it.location.details)
            binding.facilitiesDataMap = it.facilities

            val list: ArrayList<Interests> = ArrayList()
            for (i in it.activities) {
                list.add(getInterestBasedOnName(i.name))
            }
            binding.dataMap = list
        })

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
        val listOpenHours: ArrayList<DailyOpenHours> = ArrayList()
        listOpenHours.add(venueById.open.dayZero)
        listOpenHours.add(venueById.open.dayOne)
        listOpenHours.add(venueById.open.dayTwo)
        listOpenHours.add(venueById.open.dayThree)
        listOpenHours.add(venueById.open.dayFour)
        listOpenHours.add(venueById.open.dayFive)
        listOpenHours.add(venueById.open.daySix)

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
                if ((listPositions[i] - listPositions[i + 1]) > 1)
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

    private fun getInterestBasedOnName(s: String) : Interests =
        when (s) {
            "Running" -> Interests.RUNNING
            "Workout" -> Interests.WORKOUT
            "Yoga" -> Interests.YOGA
            "Football" -> Interests.FOOTBALL
            "Basketball" -> Interests.BASKETBALL
            "Tennis" -> Interests.TENNIS
            "Volley" -> Interests.VOLLEY
            "Badminton" -> Interests.BADMINTON
            "Handball" -> Interests.HANDBALL
            "Bowling" -> Interests.BOWLING
            "Volleyball" -> Interests.VOLLEYBALL
            "Table tennis" -> Interests.TABLETENNIS
            else -> Interests.RUNNING
        }

    companion object {
        private const val PLACE_PICKER_REQUEST = 1

        fun newInstance(venueId: String) : DetailInnerFragment = DetailInnerFragment(venueId)
    }

}
