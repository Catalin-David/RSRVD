package com.halcyonmobile.rsrvd.makereservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.venues.dto.VenueById
import com.halcyonmobile.rsrvd.databinding.ActivityMakeReservationBinding
import com.halcyonmobile.rsrvd.utils.showSnackbar
import com.halcyonmobile.rsrvd.venuedetails.VenueDetailActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MakeReservationActivity : AppCompatActivity() {
    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private lateinit var binding: ActivityMakeReservationBinding
    private lateinit var viewModel: MakeReservationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_reservation)
        binding.lifecycleOwner = this
        binding.dataMap = Interests.values().toMutableList()

        viewModel = ViewModelProviders.of(this).get(MakeReservationViewModel::class.java)
        val venueById = intent.getParcelableExtra<VenueById>(REQUEST_RESERVATION)

        binding.apply {
            venueNameReservation.text = venueById.name

            val list: ArrayList<Interests> = ArrayList()
            for (i in venueById.activities) {
                list.add(getInterestBasedOnName(i.name))
            }
            dataMap = list
        }

        binding.closeReservation.setOnClickListener {
           // startActivity(VenueDetailActivity.getStartIntent(this, venueById.id))
            finish()
        }

        binding.sendReservation.setOnClickListener {
            val id = venueById.activities[0].id
            val start = "2020-08-02T13:00:00.119Z"
            val end = "2020-08-02T15:00:00.119Z"

            viewModel.makeReservation(id,
                start,
                end,
                onSuccess = {
                    startActivity(Intent(this, ReservationSentActivity::class.java))
                    finish()
            },
                onFailure = {
                    binding.root.showSnackbar(getString(R.string.something_went_wrong))
            })
        }

        val hourCardsAdapter = HourCardsAdapter(onItemClick = {
            viewModel.resetPosition(it)
        })

        setUpObservers(hourCardsAdapter)
        setUpLists(hourCardsAdapter)
    }

    private fun setUpObservers(hourCardsAdapter: HourCardsAdapter) {
        viewModel.apply {
            hourCards.observe(this@MakeReservationActivity) { hourCardsAdapter.submitList(it) }
        }
    }

    private fun setUpLists(hourCardsAdapter: HourCardsAdapter) {
        setUpRecyclerView(
            binding.recyclerView,
            LinearLayoutManager(this).apply { orientation = LinearLayoutManager.HORIZONTAL },
            hourCardsAdapter
        )
    }

    private fun setUpRecyclerView(
        recyclerView: RecyclerView,
        linearLayoutManager: LinearLayoutManager,
        listAdapter: HourCardsAdapter
    ){
      recyclerView.apply {
          setHasFixedSize(false)
          layoutManager = linearLayoutManager
          adapter = listAdapter
      }
    }

    private fun getInterestBasedOnName(s: String) : Interests =
        when (s) {
            "Running" -> Interests.RUNNING
            "Workout" -> Interests.WORKOUT
            "Yoga" -> Interests.YOGA
            "Football" -> Interests.FOOTBALL
            "Basketball" -> Interests.BASKETBALL
            "Tennis" -> Interests.TENNIS
            "Badminton" -> Interests.BADMINTON
            "Handball" -> Interests.HANDBALL
            "Bowling" -> Interests.BOWLING
            "Volleyball" -> Interests.VOLLEYBALL
            "Table tennis" -> Interests.TABLETENNIS
            else -> Interests.RUNNING
        }

    companion object {
        private const val REQUEST_RESERVATION = "REQUEST_RESERVATION"

        fun getStartIntent(context: Context, venueById: VenueById) =
            Intent(context, MakeReservationActivity::class.java).putExtra(REQUEST_RESERVATION, venueById)
    }

}
