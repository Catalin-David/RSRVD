package com.halcyonmobile.rsrvd.makereservation

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.reservation.ReservationRepository
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.venues.dto.ActivitiesDto
import com.halcyonmobile.rsrvd.explorevenues.filter.FilterDate
import com.halcyonmobile.rsrvd.explorevenues.filter.FilterTime
import java.util.*
import kotlin.math.abs

class MakeReservationViewModel : ViewModel() {
    private val _hourCards: MutableLiveData<List<HourUiModel>> = MutableLiveData()
    private val _time: MutableLiveData<FilterTime> = MutableLiveData()

    val hourCards: LiveData<List<HourUiModel>> = _hourCards
    val time: LiveData<FilterTime> = _time
    private var filterDate: FilterDate

    init {
        _hourCards.value = listHours
        val calendar = Calendar.getInstance()

        filterDate = FilterDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        setInitialInterval()
    }

    fun setInitialInterval() {
        _time.value = calculateInitialInterval()
    }

    private fun calculateInitialInterval(): FilterTime {
        val calendar = Calendar.getInstance()

        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val startHour = if (currentMinute < 30) currentHour else currentHour + 1
        val startMinute = if (currentMinute < 30) 50 else 0
        val finishHour = startHour + 1
        val finishMinute = startMinute

        return FilterTime(startHour, startMinute, finishHour, finishMinute)
    }

    fun setSelected(position: HourUiModel) {
        _hourCards.value = _hourCards.value?.map { hourUiModel ->
            hourUiModel.copy(isSelected = (hourUiModel == position))
        }
    }

    fun setSelectedByStart(start: Int) {
        val hour = start / 100
        val minute = start % 100

        var diffHour = abs(hour - _time.value!!.finishHour)
        val diffMinutes = abs(minute - _time.value!!.finishMinute)

        if (minute == 0 && _time.value!!.finishMinute == 50 && hour > _time.value!!.finishHour) diffHour--

        setSelectedByInterval(diffHour*100 + diffMinutes)
    }

    fun setSelectedByFinish(finish: Int) {
        val hour = finish / 100
        val minute = finish % 100

        var diffHour = abs(hour - _time.value!!.startHour)
        val diffMinutes = abs(minute - _time.value!!.startMinute)

        if (minute == 0 && _time.value!!.startMinute == 50 && hour > _time.value!!.startHour) diffHour--

        setSelectedByInterval(diffHour*100 + diffMinutes)
    }

    private fun setSelectedByInterval(interval: Int) {
        _hourCards.value = _hourCards.value?.map { hourUiModel ->
            hourUiModel.copy(isSelected = ( returnCorrespondingHour(hourUiModel.hour) == interval))
        }
    }

    fun generateInterestList(
        list: List<ActivitiesDto>
    ): List<Interests> = list.map { Interests.getInterestBasedOnName(it.name) }.toList()

    fun makeReservation(
        id: String,
        start: String,
        end: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        ReservationRepository.createReservation(id, start, end, onSuccess, onFailure)
    }

    fun setStart(start: Int) {
        val hour = start / 100
        val minute = start % 100
        _time.value = FilterTime(hour, minute, _time.value!!.finishHour, _time.value!!.finishMinute)
    }

    fun setFinish(finish: Int) {
        val hour = finish / 100
        val minute = finish % 100
        _time.value = FilterTime(_time.value!!.startHour, _time.value!!.startMinute, hour, minute)
    }

    fun adjustFinish(value: Int) {
        var hour = value / 100 + time.value!!.startHour
        var minute = value % 100 + time.value!!.startMinute

        if (minute == 100) {
            hour += 1
            minute = 0
        }

        _time.value = FilterTime(_time.value!!.startHour, _time.value!!.startMinute, hour, minute)
    }

    fun returnCorrespondingHour(hour: String): Int =
        when (hour) {
            "1 H" -> 100
            "1:30 H" -> 150
            "2 H" -> 200
            "2:30 H" -> 250
            "3 H" -> 300
            "3:30 H" -> 350
            "4 H" -> 400
            else -> 100
        }

    companion object {
        private val listHours = listOf(
            HourUiModel("1 H", isSelected = true),
            HourUiModel("1:30 H", isSelected = false),
            HourUiModel("2 H", isSelected = false),
            HourUiModel("2:30 H", isSelected = false),
            HourUiModel("3 H", isSelected = false),
            HourUiModel("3:30 H", isSelected = false),
            HourUiModel("4 H", isSelected = false)
        )
    }
}
