package com.halcyonmobile.rsrvd.core.reservation

import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReservationApi {
    @GET("reservations")
    fun get(): Call<List<ReservationDto>>

    @POST("reservations")
    fun createReservation(@Body reservationRequestDto: ReservationRequestDto) : Call<Unit>
}