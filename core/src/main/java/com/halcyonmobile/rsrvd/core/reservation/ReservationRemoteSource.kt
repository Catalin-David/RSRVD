package com.halcyonmobile.rsrvd.core.reservation

import com.halcyonmobile.rsrvd.core.reservation.api.ReservationApi
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import com.halcyonmobile.rsrvd.core.shared.RetrofitManager

class ReservationRemoteSource {
    private val reservationApi = RetrofitManager.retrofitWithAuthentication!!.create(ReservationApi::class.java)

    fun get(updateState: (List<ReservationDto>?) -> Unit) {
        reservationApi.get().enqueue(GetReservationsHandler(updateState))
    }
}
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
