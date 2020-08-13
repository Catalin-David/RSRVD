package com.halcyonmobile.rsrvd.core.reservation

import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationApi {
    @GET("reservations")
    fun get(): Call<List<ReservationDto>>

    @POST("reservations")
    fun createReservation(@Body reservationRequestDto: ReservationRequestDto): Call<Unit>

    @GET("reservations/{reservationId}")
    fun getWithId(@Path("reservationId") id: String): Call<ReservationDto>

    @POST("reservations/{reservationId}/cancel")
    fun cancelReservationWithId(@Path("reservationId") id: String): Call<Any>
}