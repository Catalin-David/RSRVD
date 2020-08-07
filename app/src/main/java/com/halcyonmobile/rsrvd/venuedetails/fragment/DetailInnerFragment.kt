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

    private fun getDay(i: Int): String =
        when (i) {
            0 -> "MON"
            1 -> "TUE"
            2 -> "WED"
            3 -> "THU"
            4 -> "FRI"
            5 -> "SAT"
            6 -> "SUN"
            else -> "NULL"
        }

    private fun getHour(f: Float): String {
        val hour: Int = if ( f.toInt() > 12 ) f.toInt() - 12
        else f.toInt()

        val minutes: Int = ((f - f.toInt()) * 100).toInt()
        var s = "$hour:"

        s += if ( minutes != 0 ) "$minutes"
        else "00"
        s += if (f <= 12) " AM"
        else " PM"

        return s
    }

    private fun getVenueProgram(it: VenueById): String {
        val listOpenHours: ArrayList<DailyOpenHours> = ArrayList()
        listOpenHours.add(it.open.dayZero)
        listOpenHours.add(it.open.dayOne)
        listOpenHours.add(it.open.dayTwo)
        listOpenHours.add(it.open.dayThree)
        listOpenHours.add(it.open.dayFour)
        listOpenHours.add(it.open.dayFive)
        listOpenHours.add(it.open.daySix)

        var start = it.open.dayZero.start
        var end = it.open.dayZero.end
        val listPositions: ArrayList<Int> = ArrayList()
        for (i in listOpenHours) {
            if ( i.start != start || i.end != end ) {
                start = i.start
                end = i.end

                listPositions.add(listOpenHours.indexOf(i))
            }
        }

        var result = ""
        for (i in 0 until listPositions.size) {
            val day = getDay( listPositions[i] - 1 )
            val hStart = getHour(listOpenHours[ listPositions[i] ].start)
            val hEnd = getHour(listOpenHours[ listPositions[i] ].end)

            result += when {
                i == (listPositions.size - 1) -> {
                    "$day, $hStart - $hEnd\n"
                }
                (listPositions[i] - listPositions[i + 1]) > 1 -> {
                    val dayNext = getDay( listPositions[i] )
                    "$day - $dayNext, $hStart - $hEnd\n"
                }
                else -> {
                    "$day, $hStart - $hEnd\n"
                }
            }
        }

        val index = if ( listPositions.isEmpty() ) 0
        else listPositions.size - 1
        val day = if ( listPositions.isEmpty() ) getDay(index)
        else getDay(listPositions[index])
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
         //   "Bowling" -> Interests.BOWLING
            "Volleyball" -> Interests.VOLLEY
          //  "Table tennis" -> Interests.TABLETENNIS
            else -> Interests.RUNNING
        }

    companion object {
        private const val PLACE_PICKER_REQUEST = 1

        fun newInstance(venueId: String) : DetailInnerFragment = DetailInnerFragment(venueId)
    }

}
