package com.halcyonmobile.rsrvd.reservationdetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import com.halcyonmobile.rsrvd.core.reservation.repository.ReservationRepository

class ReservationDetailsViewModel: ViewModel() {
    private val _reservation: MutableLiveData<ReservationDto> = MutableLiveData()
    val reservation: LiveData<ReservationDto> = _reservation

    fun loadReservation(reservationId: String){
        ReservationRepository.getReservationWithId(reservationId){
            it?.let {
                _reservation.value = it
            }
        }
    }
}