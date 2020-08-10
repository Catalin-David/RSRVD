package com.halcyonmobile.rsrvd.core.reservation

import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReservationApi {
    @POST("reservations")
    fun createReservation(@Body reservationRequestDto: ReservationRequestDto) : Call<Unit>
}
