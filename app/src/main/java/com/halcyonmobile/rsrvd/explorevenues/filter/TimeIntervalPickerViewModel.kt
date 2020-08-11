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

    fun init(
        startPicker: RecyclerView,
        finishPicker: RecyclerView,
        startLayoutManager: LinearLayoutManager,
        finishLayoutManager: LinearLayoutManager,
        startPickerAdapter: TimePickerAdapter,
        finishPickerAdapter: TimePickerAdapter
    ) {
        setUpPicker(startPicker, _start, startLayoutManager, lastStartIndex, startPickerAdapter)
        setUpPicker(finishPicker, _end, finishLayoutManager, lastFinishIndex, finishPickerAdapter)

        scrollToNow(startPicker, finishPicker)
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

    private fun scrollToNow(startPicker: RecyclerView, finishPicker: RecyclerView) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        startPicker.layoutManager?.scrollToPosition(Times.hours.indexOf(if (minute < 30) hour * 100 + 50 else (hour + 1) * 100))
        finishPicker.layoutManager?.scrollToPosition(Times.hours.indexOf(if (minute < 30) (hour + 1) * 100 else (hour + 1) * 100 + 50))

        // Trigger snap helper, after automated scrolling to current position
        startPicker.smoothScrollBy(1, 0)
        finishPicker.smoothScrollBy(1, 0)
    }
}