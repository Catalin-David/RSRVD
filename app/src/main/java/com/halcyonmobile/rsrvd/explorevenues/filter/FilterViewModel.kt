package com.halcyonmobile.rsrvd.explorevenues.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.venues.dto.FilterLocation
import java.util.*

class FilterViewModel : ViewModel() {
    private val _activities = MutableLiveData<List<Interests>>()
    val activities: LiveData<List<Interests>> = _activities

    var filterDate: FilterDate
    private var filterTime: FilterTime

    init {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val startHour = calendar.get(Calendar.HOUR_OF_DAY)
        val startMinute = calendar.get(Calendar.MINUTE)
        val finishHour = startHour // TODO
        val finishMinute = startMinute // TODO

        filterDate = FilterDate(year, month, day)
        filterTime = FilterTime(startHour, startMinute, finishHour, finishMinute)
    }

    fun setDate(year: Int, month: Int, day: Int) {
        filterDate = FilterDate(year, month, day)
    }

    fun setInterval(startHour: Int, startMinute: Int, finishHour: Int, finishMinute: Int) {
        filterTime = FilterTime(startHour, startMinute, finishHour, finishMinute)
    }

    fun getAvailability(): FilterDateTime =
        FilterDateTime(
            filterDate.year,
            filterDate.month,
            filterDate.day,
            filterTime.startHour,
            filterTime.startMinute,
            filterTime.finishHour,
            filterTime.finishMinute
        )
}