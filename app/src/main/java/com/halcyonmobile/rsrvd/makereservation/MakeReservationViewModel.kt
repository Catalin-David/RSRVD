package com.halcyonmobile.rsrvd.makereservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MakeReservationViewModel : ViewModel() {
    private val _hourCards: MutableLiveData<List<HourUiModel>> = MutableLiveData()

    val hourCards: LiveData<List<HourUiModel>> = _hourCards

    init {
        initializeHourCards()
    }

    private fun initializeHourCards() {
        listHours.let {
            _hourCards.value = it
        }
    }

    fun resetPosition(position: Int) {
        listHours.let {
            _hourCards.value = it
        }

        _hourCards.value?.get(position)?.isSelected = true
    }

    companion object {
        private val listHours = listOf(
            HourUiModel("1 H", isSelected = false),
            HourUiModel("1:30 H", isSelected = false),
            HourUiModel("2 H", isSelected = false),
            HourUiModel("2:30 H", isSelected = false),
            HourUiModel("3 H", isSelected = false),
            HourUiModel("3:30 H", isSelected = false),
            HourUiModel("4 H", isSelected = false)
        )
    }
}
