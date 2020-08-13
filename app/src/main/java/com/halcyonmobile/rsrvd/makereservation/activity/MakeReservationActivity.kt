package com.halcyonmobile.rsrvd.makereservation.activity

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationRequestDto
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.databinding.ActivityMakeReservationBinding
import com.halcyonmobile.rsrvd.explorevenues.ExploreFragment
import com.halcyonmobile.rsrvd.explorevenues.filter.*
import com.halcyonmobile.rsrvd.makereservation.HourCardsAdapter
import com.halcyonmobile.rsrvd.makereservation.MakeReservationViewModel
import com.halcyonmobile.rsrvd.onboarding.InterestView
import com.halcyonmobile.rsrvd.profile.ProfileFragment
import com.halcyonmobile.rsrvd.reservation.ReservationFragment
import com.halcyonmobile.rsrvd.shared.FragmentDecision
import com.halcyonmobile.rsrvd.utils.showSnackbar
import com.halcyonmobile.rsrvd.venuedetails.VenueDetailViewModel
import org.joda.time.DateTime

class MakeReservationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeReservationBinding
    private lateinit var viewModel: MakeReservationViewModel
    private lateinit var venueDetailViewModel: VenueDetailViewModel
    private lateinit var filterViewModel: FilterViewModel
    private lateinit var timeIntervalPickerViewModel: TimeIntervalPickerViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_reservation)
        viewModel = ViewModelProviders.of(this).get(MakeReservationViewModel::class.java)
        timeIntervalPickerViewModel = ViewModelProviders.of(this).get(TimeIntervalPickerViewModel::class.java)
        venueDetailViewModel = ViewModelProviders.of(this).get(VenueDetailViewModel::class.java)
        filterViewModel = ViewModelProviders.of(this).get(FilterViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.detailViewModel = venueDetailViewModel

        intent.getStringExtra(MESSAGE)?.let {
            binding.root.showSnackbar(it)
            println(it)
        }

        val venueById: String? = intent.getStringExtra(REQUEST_RESERVATION)
        venueById?.let {
            venueDetailViewModel.getVenue(it)
        }

        binding.closeReservation.setOnClickListener {
            finish()
        }

        binding.sendReservation.setOnClickListener {
            var filterDateTime: FilterDateTime? = null
            try {
                filterDateTime = filterViewModel.getAvailability()
            } catch (filterDurationException: FilterDurationException) {
                binding.root.showSnackbar(getString(R.string.interval_too_short))
            } catch (filterDateException: FilterDateException) {
                binding.root.showSnackbar(getString(R.string.filter_date_exception))
            }

            if (verifyInterests() == null)
                binding.root.showSnackbar(getString(R.string.please_select_activity))
            else {
                val id = venueDetailViewModel.hashMapActivities.value?.get(verifyInterests())

                while (id == null) {}

                filterDateTime?.let {
                    val dateStart = DateTime(it.year, it.month + 1, it.day, it.startHour, it.startMinute * 60 / 100).toString()
                    val dateFinish = DateTime(it.year, it.month + 1, it.day, it.finishHour, it.finishMinute * 60 / 100).toString()

                    Log.d(ContentValues.TAG, "$dateStart $dateFinish")

                    if (id == null) {
                        binding.root.showSnackbar("Overbooked!")
                    }

                    id?.let { activityId ->
                        Log.d(ContentValues.TAG, id)

                        startActivity(
                            ReservationSentActivity.getStartIntent(
                                this,
                                ReservationRequestDto(activityId, dateStart, dateFinish),
                                venueById!!
                            )
                        )

                    }
                }
            }
        }

        //setup for the date picker
        binding.apply {
            dateText.text = getString(
                R.string.filter_date,
                filterViewModel.filterDate.day,
                Times.months[filterViewModel.filterDate.month],
                filterViewModel.filterDate.year
            )

            datePicker.setOnClickListener {
                DatePickerDialog(
                    this@MakeReservationActivity,
                    R.style.DatePickerDialogStyle,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        dateText.text = getString(R.string.filter_date, dayOfMonth, Times.months[month], year)
                        filterViewModel.setDate(year, month, dayOfMonth)
                    },
                    filterViewModel.filterDate.year,
                    filterViewModel.filterDate.month,
                    filterViewModel.filterDate.day
                ).show()
            }
        }

        viewModel.setInitialInterval()

        val hourCardsAdapter =
            HourCardsAdapter(onItemClick = {
                viewModel.setSelected(it)

                viewModel.adjustFinish(viewModel.returnCorrespondingHour(it.hour))

                viewModel.time.value?.let { filterTime ->
                    binding.intervalPicker.finishPicker.layoutManager?.scrollToPosition(
                        Times.hours.indexOf(
                            filterTime.finishHour * 100 + filterTime.finishMinute
                        )
                    )
                    // Trigger snap helper, after automated scrolling to current position
                    binding.intervalPicker.finishPicker.smoothScrollBy(5, 0)
                }
            })

        timeIntervalPickerViewModel.apply {
            setup(
                binding.intervalPicker.startPicker,
                binding.intervalPicker.finishPicker,
                LinearLayoutManager(this@MakeReservationActivity).apply { orientation = LinearLayoutManager.HORIZONTAL },
                LinearLayoutManager(this@MakeReservationActivity).apply { orientation = LinearLayoutManager.HORIZONTAL },
                TimePickerAdapter().apply { submitList(Times.hours) },
                TimePickerAdapter().apply { submitList(Times.hours) },
                viewModel.time.value
            )

            start.observe(this@MakeReservationActivity) {
                viewModel.setStart(it)
                filterViewModel.setStart(it)
                viewModel.setSelectedByStart(it)
            }
            end.observe(this@MakeReservationActivity) {
                viewModel.setFinish(it)
                filterViewModel.setFinish(it)
                viewModel.setSelectedByFinish(it)
            }
        }

        viewModel.hourCards.observe(this@MakeReservationActivity) { hourCardsAdapter.submitList(it) }

        setUpLists(hourCardsAdapter)
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
    ) {
        recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = linearLayoutManager
            adapter = listAdapter
        }
    }

    private fun verifyInterests(): Interests? {
        var interestView: Interests? = null
        val list = binding.interestsGrid.children
            .filterIsInstance<InterestView>()
            .mapIndexed { index, view -> if (view.isChecked()) Interests.values()[index] else null }
            .filterNotNull()
            .toList()

        list.map {
            if (it.name != null) interestView = it
        }

        return interestView
    }

    companion object {
        private const val REQUEST_RESERVATION = "REQUEST_RESERVATION"
        private const val MESSAGE = "message"

        fun getStartIntent(context: Context, venueById: String) =
            Intent(context, MakeReservationActivity::class.java).putExtra(
                REQUEST_RESERVATION, venueById
            )

        fun startIntentWithMessage(context: Context, venueId: String, message: String) =
            Intent(context, MakeReservationActivity::class.java)
                .putExtra(REQUEST_RESERVATION, venueId)
                .putExtra(MESSAGE, message)
    }

}
