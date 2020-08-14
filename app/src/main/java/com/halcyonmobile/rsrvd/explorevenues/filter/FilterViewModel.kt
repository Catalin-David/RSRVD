package com.halcyonmobile.rsrvd.explorevenues.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.shared.Interests
import java.util.Calendar

class FilterViewModel : ViewModel() {
    private val _activities = MutableLiveData<List<Interests>>()
    val activities: LiveData<List<Interests>> = _activities

    var filterDate: FilterDate
    private var filterTime: FilterTime

    init {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val startHour = if (currentMinute < 30) currentHour else currentHour + 1
        val startMinute = if (currentMinute < 30) 50 else 0
        val finishHour = if (startMinute < 50) startHour else startHour + 1
        val finishMinute = if (startMinute < 50) 50 else 0

        filterDate = FilterDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        filterTime = FilterTime(startHour, startMinute, finishHour, finishMinute)
    }

    fun setDate(year: Int, month: Int, day: Int) {
        filterDate = FilterDate(year, month, day)
    }

    fun setStart(start: Int) {
        val hour = start / 100
        val minute = start % 100
        filterTime = FilterTime(hour, minute, filterTime.finishHour, filterTime.finishMinute)
    }

    fun setFinish(finish: Int) {
        val hour = finish / 100
        val minute = finish % 100
        filterTime = FilterTime(filterTime.startHour, filterTime.startMinute, hour, minute)
    }

    private fun checkTime(): Boolean {
        val now = Calendar.getInstance()

        return (filterTime.finishHour > filterTime.startHour ||
                (filterTime.finishHour == filterTime.startHour && filterTime.finishMinute > filterTime.startMinute)) &&
                (filterTime.startHour > (now.get(Calendar.HOUR_OF_DAY)) ||
                        ((now.get(Calendar.HOUR_OF_DAY) == filterTime.startHour) && (filterTime.startMinute > now.get(Calendar.MINUTE))))
    }

    private fun checkDate(): Boolean {
        val now = Calendar.getInstance()

        return filterDate.year >= now.get(Calendar.YEAR) && filterDate.month >= now.get(Calendar.MONTH) && filterDate.day >= now.get(Calendar.DAY_OF_MONTH)
    }

    fun getAvailability(): FilterDateTime {

        if (!checkTime()) {
            throw FilterDurationException()
        }

        if (!checkDate()) {
            throw FilterDateException()
        }

        return FilterDateTime(
            filterDate.year,
            filterDate.month,
            filterDate.day,
            filterTime.startHour,
            filterTime.startMinute,
            filterTime.finishHour,
            filterTime.finishMinute
        )
    }
}
