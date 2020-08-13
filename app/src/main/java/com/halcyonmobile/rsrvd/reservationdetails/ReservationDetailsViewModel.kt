package com.halcyonmobile.rsrvd.reservationdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.reservation.ReservationRepository
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import com.halcyonmobile.rsrvd.core.reservation.model.ReservationState

class ReservationDetailsViewModel : ViewModel() {
    private val _reservation: MutableLiveData<ReservationDto> = MutableLiveData()
    private val _showCancelButton: MutableLiveData<Boolean> = MutableLiveData(true)
    private val _cancelDesign: MutableLiveData<Boolean> = MutableLiveData(false)

    val reservation: LiveData<ReservationDto> = _reservation
    val showCancelButton: LiveData<Boolean> = _showCancelButton
    val cancelDesign: LiveData<Boolean> = _cancelDesign

    fun cancelReservation(id: String) = ReservationRepository.cancelReservation(id)

    fun loadReservation(reservationId: String) {
        ReservationRepository.getReservationWithId(reservationId) {
            it?.let {
                _reservation.value = it
                when (it.state) {
                    ReservationState.CONFIRMED -> {
                        _showCancelButton.value = true
                        _cancelDesign.value = false
                    }
                    ReservationState.COMPLETED -> {
                        _showCancelButton.value = false
                        _cancelDesign.value = false
                    }
                    ReservationState.CANCELLED -> {
                        _showCancelButton.value = false
                        _cancelDesign.value = true
                    }
                }
            }
        }
    }
}