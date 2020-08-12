package com.halcyonmobile.rsrvd.makereservation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.venues.dto.VenueById
import com.halcyonmobile.rsrvd.databinding.ActivityMakeReservationBinding
import com.halcyonmobile.rsrvd.explorevenues.filter.*
import com.halcyonmobile.rsrvd.utils.showSnackbar

class MakeReservationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeReservationBinding
    private lateinit var viewModel: MakeReservationViewModel
    private lateinit var timeIntervalPickerViewModel: TimeIntervalPickerViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_reservation)
        viewModel = ViewModelProviders.of(this).get(MakeReservationViewModel::class.java)
        timeIntervalPickerViewModel = ViewModelProviders.of(this).get(TimeIntervalPickerViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val venueById: VenueById? = intent.getParcelableExtra(REQUEST_RESERVATION)

        binding.apply {
         //   dataMap = viewModel.generateInterestList(venueById.activities)
        }

        binding.closeReservation.setOnClickListener {
            finish()
        }

        binding.sendReservation.setOnClickListener {

            startActivity(Intent(this, ReservationSentActivity::class.java))
            viewModel.makeReservation(
                venueById!!.activities[0].id,
                "2020-08-02T13:00:00.119Z",
                "2020-08-02T15:00:00.119Z",
                onSuccess = {
                    startActivity(Intent(this, ReservationSentActivity::class.java))
                    finish()
            },
                onFailure = {
                    binding.root.showSnackbar(getString(R.string.something_went_wrong))
            })
        }

        val hourCardsAdapter = HourCardsAdapter(onItemClick = {
            viewModel.setSelected(it)
        })

        timeIntervalPickerViewModel.apply {
            setup(binding.intervalPicker.startPicker,
                binding.intervalPicker.finishPicker,
                LinearLayoutManager(this@MakeReservationActivity).apply { orientation = LinearLayoutManager.HORIZONTAL },
                LinearLayoutManager(this@MakeReservationActivity).apply { orientation = LinearLayoutManager.HORIZONTAL },
                TimePickerAdapter().apply { submitList(Times.hours) },
                TimePickerAdapter().apply { submitList(Times.hours) },
                viewModel.time.value
            )

            start.observe(this@MakeReservationActivity) { viewModel.setStart(it) }
            end.observe(this@MakeReservationActivity) { viewModel.setFinish(it) }
        }

        viewModel.hourCards.observe(this@MakeReservationActivity) { hourCardsAdapter.submitList(it) }

        setUpLists(hourCardsAdapter)
    }
/*
val year: Int,
    val month: Int,
    val day: Int,
    val startHour: Int,
    val startMinute: Int,
    val finishHour: Int,
    val finishMinute: Int
 */
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

    companion object {
        private const val REQUEST_RESERVATION = "REQUEST_RESERVATION"

        fun getStartIntent(context: Context, venueById: VenueById) =
            Intent(context, MakeReservationActivity::class.java).putExtra(REQUEST_RESERVATION, venueById)
    }

}
