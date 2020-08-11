package com.halcyonmobile.rsrvd.makereservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.reservation.ReservationRepository

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

    fun resetPosition(position: HourUiModel) {
        _hourCards.value = _hourCards.value?.mapIndexed { _, hourUiModel ->
            if(hourUiModel == position) hourUiModel.copy(isSelected = true)
            else hourUiModel.copy(isSelected = false)
        }
    }

    fun makeReservation(
        id: String,
        start: String,
        end: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit) {
        ReservationRepository.createReservation(id, start, end, onSuccess, onFailure)
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
