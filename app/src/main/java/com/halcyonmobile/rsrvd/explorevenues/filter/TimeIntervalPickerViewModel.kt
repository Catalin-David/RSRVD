package com.halcyonmobile.rsrvd.explorevenues.filter

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.explorevenues.MarginDecorator
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.M)
class TimeIntervalPickerViewModel : ViewModel() {
    private val _start = MutableLiveData<Int>()
    private val _end = MutableLiveData<Int>()

    val start: LiveData<Int> = _start
    val end: LiveData<Int> = _end

    private val lastStartIndex = MutableLiveData(-1)
    private val lastFinishIndex = MutableLiveData(-1)

    fun setup(
        startPicker: RecyclerView,
        finishPicker: RecyclerView,
        startLayoutManager: LinearLayoutManager,
        finishLayoutManager: LinearLayoutManager,
        startPickerAdapter: TimePickerAdapter,
        finishPickerAdapter: TimePickerAdapter,
        initialInterval: FilterTime? = null
    ) {
        setUpPicker(startPicker, _start, startLayoutManager, lastStartIndex, startPickerAdapter)
        setUpPicker(finishPicker, _end, finishLayoutManager, lastFinishIndex, finishPickerAdapter)

        scrollTo(initialInterval, startPicker, finishPicker)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpPicker(
        picker: RecyclerView,
        storage: MutableLiveData<Int>,
        layoutManager: LinearLayoutManager,
        lastIndex: MutableLiveData<Int>,
        timePickerAdapter: TimePickerAdapter
    ) {
        val offset = Resources.getSystem().displayMetrics.widthPixels / 2

        picker.apply {
            setHasFixedSize(true)
            this.layoutManager = layoutManager
            adapter = timePickerAdapter
            addItemDecoration(MarginDecorator(right = true))
            LinearSnapHelper().attachToRecyclerView(this)
            setPadding(offset, 0, offset, 0)
            setOnScrollChangeListener { _, _, _, _, _ ->
                val index = (layoutManager.findFirstVisibleItemPosition() + layoutManager.findLastVisibleItemPosition()) / 2

                if (index != -1 && index != lastIndex.value) {
                    storage.value = timePickerAdapter.currentList[index]

                    Times.hours.indices.map { i ->
                        picker.findViewHolderForLayoutPosition(i)?.let {
                            (it as TimePickerAdapter.TimeStepViewHolder).apply { if (i == index) select() else unSelect() }
                        }
                    }

                    lastIndex.value = index
                }
            }
        }
    }

    private fun scrollTo(initialInterval: FilterTime?, startPicker: RecyclerView, finishPicker: RecyclerView) {
        val startIndex: Int
        val finishIndex: Int

        if (initialInterval != null) {
            startIndex = initialInterval.startHour * 100 + initialInterval.startMinute
            finishIndex = initialInterval.finishHour * 100 + initialInterval.finishMinute
        } else {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            startIndex = if (currentMinute < 30) currentHour * 100 + 50 else (currentHour + 1) * 100
            finishIndex = if (currentMinute < 30) (currentHour + 1) * 100 else (currentHour + 1) * 100 + 50
        }

        startPicker.layoutManager?.scrollToPosition(Times.hours.indexOf(startIndex))
        finishPicker.layoutManager?.scrollToPosition(Times.hours.indexOf(finishIndex))

        // Trigger snap helper, after automated scrolling to current position
        startPicker.smoothScrollBy(5, 0)
        finishPicker.smoothScrollBy(5, 0)
    }
}