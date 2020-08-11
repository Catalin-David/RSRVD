package com.halcyonmobile.rsrvd.reservationdetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.reservation.dto.ActivityDto
import com.halcyonmobile.rsrvd.core.reservation.dto.PriceDto
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import com.halcyonmobile.rsrvd.core.reservation.model.ReservationState
import com.halcyonmobile.rsrvd.core.reservation.repository.ReservationRepository
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.venues.dto.Open
import com.halcyonmobile.rsrvd.core.venues.dto.StartEndHours
import com.halcyonmobile.rsrvd.core.venues.dto.Venue

class ReservationDetailsViewModel : ViewModel() {
    private val _reservation: MutableLiveData<ReservationDto> = MutableLiveData()
    val reservation: LiveData<ReservationDto> = _reservation

    fun cancelReservation(id: String) = ReservationRepository.cancelReservation(id)
    fun loadReservation(reservationId: String) {
//        ReservationRepository.getReservationWithId(reservationId){
//            it?.let {
//                _reservation.value = it
//            }
//        }
//      USED FOR TESTING
        val venue = Venue(
            "1",
            "Baza sportiva Gheorgheni",
            "",
            "https://www.clujlife.com/wp-content/uploads/2016/12/baza-sportiva-gheorghieni-cluj.jpg",
            Location(),
            Open(
                StartEndHours(1f, 2f),
                StartEndHours(1f, 2f),
                StartEndHours(1f, 2f),
                StartEndHours(1f, 2f),
                StartEndHours(1f, 2f),
                StartEndHours(1f, 2f),
                StartEndHours(1f, 2f)
            ),
            emptyList()
        )

        _reservation.value = ReservationDto(
            id = "1",
            venue = venue,
            activity = ActivityDto(
                "1",
                venue,
                "Running",
                10,
                PriceDto(
                    10,
                    2,
                    "hour"
                )
            ),
            date = "2020-08-12T16:00:00.119Z",
            endDate = "2020-08-12T18:00:00.119Z",
            state = ReservationState.CONFIRMED,
            creator = "David Catalin"
        )
    }
}