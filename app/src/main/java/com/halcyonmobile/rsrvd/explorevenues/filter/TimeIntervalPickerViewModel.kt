package com.halcyonmobile.rsrvd.explorevenues.filter

import android.content.Context
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bekawestberg.loopinglayout.library.LoopingLayoutManager
import com.bekawestberg.loopinglayout.library.LoopingSnapHelper
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.explorevenues.MarginDecorator
import java.lang.Exception
import java.util.Calendar

class TimeIntervalPickerViewModel : ViewModel() {
    private val _start = MutableLiveData<Int>()
    private val _end = MutableLiveData<Int>()

    private lateinit var startLayoutManager: LinearLayoutManager
    private lateinit var finishLayoutManager: LinearLayoutManager

    private val hours = (0 until 2400 step 50).toList()

    val start: LiveData<Int> = _start
    val end: LiveData<Int> = _end

    @RequiresApi(Build.VERSION_CODES.M)
    fun init(context: Context, startPicker: RecyclerView, finishPicker: RecyclerView) {
        val startPickerAdapter = TimePickerAdapter().apply { submitList(hours) }
        startLayoutManager = LinearLayoutManager(context).apply { orientation = LoopingLayoutManager.HORIZONTAL }
        val startHandler = Handler()
        val startRunnable = Runnable {
            val index = (startLayoutManager.findFirstVisibleItemPosition() + startLayoutManager.findLastVisibleItemPosition()) / 2
            _start.value = startPickerAdapter.currentList[index]

            // Un-selecting the items
            hours.indices.map {
                try {
                    (startPicker.findViewHolderForAdapterPosition(it) as TimePickerAdapter.TimeStepViewHolder).unSelect()
                } catch (e: Exception) {
                    e.message?.let { Log.d(context.getString(R.string.timepicker_exception), e.message!!) }
                }
            }
            // Selecting the middle one
            (startPicker.findViewHolderForAdapterPosition(index) as TimePickerAdapter.TimeStepViewHolder).select()
        }

        startPicker.apply {
            setHasFixedSize(true)
            layoutManager = startLayoutManager
            adapter = startPickerAdapter
            addItemDecoration(MarginDecorator(right = true))
            LoopingSnapHelper().attachToRecyclerView(this)
            setOnScrollChangeListener { _, _, _, _, _ ->
                startHandler.removeCallbacks(startRunnable)
                startHandler.postDelayed(startRunnable, DEBOUNCE_DURATION)
            }
        }

        val finishPickerAdapter = TimePickerAdapter().apply { submitList(hours) }
        finishLayoutManager = LinearLayoutManager(context).apply { orientation = LoopingLayoutManager.HORIZONTAL }
        val finishHandler = Handler()
        val finishRunnable = Runnable {
            val index = (finishLayoutManager.findFirstVisibleItemPosition() + finishLayoutManager.findLastVisibleItemPosition()) / 2
            _end.value = finishPickerAdapter.currentList[index]

            // Un-selecting the items
            hours.indices.map {
                try {
                    (finishPicker.findViewHolderForAdapterPosition(it) as TimePickerAdapter.TimeStepViewHolder).unSelect()
                } catch (e: Exception) {
                    e.message?.let { Log.d(context.getString(R.string.timepicker_exception), e.message!!) }
                }
            }
            // Selecting the middle one
            (finishPicker.findViewHolderForAdapterPosition(index) as TimePickerAdapter.TimeStepViewHolder).select()
        }

        finishPicker.apply {
            setHasFixedSize(true)
            layoutManager = finishLayoutManager
            adapter = finishPickerAdapter
            addItemDecoration(MarginDecorator(right = true))
            LoopingSnapHelper().attachToRecyclerView(this)
            setOnScrollChangeListener { _, _, _, _, _ ->
                finishHandler.removeCallbacks(finishRunnable)
                finishHandler.postDelayed(finishRunnable, DEBOUNCE_DURATION)
            }
        }

        scrollToNow()
    }

    private fun scrollToNow() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val now = if (minute < 30) hour * 100 + 50 else (hour + 1) * 100
        startLayoutManager.scrollToPosition(hours.indexOf(now))

        val then = if (minute < 30) (hour + 1) * 100 else (hour + 1) * 100 + 50
        finishLayoutManager.scrollToPosition(hours.indexOf(then))
    }

    companion object {
        const val DEBOUNCE_DURATION = 300L
    }
}