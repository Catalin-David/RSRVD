package com.halcyonmobile.rsrvd.makereservation.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.CheckBox
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
import com.halcyonmobile.rsrvd.explorevenues.filter.*
import com.halcyonmobile.rsrvd.makereservation.HourCardsAdapter
import com.halcyonmobile.rsrvd.makereservation.MakeReservationViewModel
import com.halcyonmobile.rsrvd.onboarding.InterestView
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

        if (intent.getStringExtra(REQUEST_RESERVATION) == null) {
            startActivity(MainActivity.instanceAfterReservation(this, FragmentDecision.EXPLORE))
        }

        val venueById: String = intent.getStringExtra(REQUEST_RESERVATION)!!

        venueDetailViewModel.getVenue(venueById)

        binding.closeReservation.setOnClickListener {
            finish()
        }

        binding.sendReservation.setOnClickListener {
            if (verifyInterests() == null)
                binding.root.showSnackbar(getString(R.string.please_select_activity))
            else {
                try {
                    val filterDateTime = filterViewModel.getAvailability()

                    while (venueDetailViewModel.hashMapActivities.value == null) {
                        venueDetailViewModel.getVenue(venueById)
                    }
                    val id = venueDetailViewModel.hashMapActivities.value!![verifyInterests()]

                    val dateStart = DateTime(
                        filterDateTime.year,
                        filterDateTime.month + 1,
                        filterDateTime.day,
                        filterDateTime.startHour,
                        filterDateTime.startMinute * 60 / 100
                    ).toString()

                    val dateFinish = DateTime(
                        filterDateTime.year,
                        filterDateTime.month + 1,
                        filterDateTime.day,
                        filterDateTime.finishHour,
                        filterDateTime.finishMinute * 60 / 100
                    ).toString()

                    startActivity(ReservationSentActivity.getStartIntent(this, ReservationRequestDto(id!!, dateStart, dateFinish), venueById))

                } catch (filterDurationException: FilterDurationException) {
                    binding.root.showSnackbar(getString(R.string.interval_too_short))
                } catch (filterDateException: FilterDateException) {
                    binding.root.showSnackbar(getString(R.string.filter_date_exception))
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

        viewModel.hourCards.observe(this@MakeReservationActivity)
        { hourCardsAdapter.submitList(it) }

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
        val list = binding.interestsGrid.children
            .filterIsInstance<InterestView>()
            .map { if (it.isChecked()) Interests.valueOf((it.getViewById(R.id.interest_button) as CheckBox).text as String) else null }
            .filterNotNull()
            .toList()

        return if (list.isNotEmpty()) list[0] else null
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
                .apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TOP }
                .putExtra(REQUEST_RESERVATION, venueId)
                .putExtra(MESSAGE, message)
    }

}
