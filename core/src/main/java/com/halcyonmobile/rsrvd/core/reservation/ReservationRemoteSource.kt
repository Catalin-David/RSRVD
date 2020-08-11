package com.halcyonmobile.rsrvd.core.reservation

import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationRequestDto
import com.halcyonmobile.rsrvd.core.shared.RetrofitManager

internal class ReservationRemoteSource {
    private val reservationApi = RetrofitManager.retrofitWithAuthentication!!.create(ReservationApi::class.java)

    fun createReservation(
        id: String,
        star: String,
        end: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit) {
        reservationApi.createReservation(ReservationRequestDto(id, star, end)).enqueue(CreateReservationResponseHandler(onSuccess, onFailure))
    }
}
