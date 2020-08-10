package com.halcyonmobile.rsrvd.core.reservation.api

import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import retrofit2.Call
import retrofit2.http.GET

interface ReservationApi {
    @GET("reservations")
    fun get(): Call<List<ReservationDto>>
}
