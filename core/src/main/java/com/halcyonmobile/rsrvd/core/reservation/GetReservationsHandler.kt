package com.halcyonmobile.rsrvd.core.reservation

import android.util.Log
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetReservationsHandler(private val callback: (List<ReservationDto>?) -> Unit): Callback<List<ReservationDto>> {
    override fun onFailure(call: Call<List<ReservationDto>>, t: Throwable) {
        callback(emptyList())
        Log.d("RESERVATION REMOTE SRC", t.message ?: "NO MESSAGE")
    }

    override fun onResponse(call: Call<List<ReservationDto>>, response: Response<List<ReservationDto>>) {
        Log.d("TAG", response.body().toString()) // LOOKS LIKE THERE ARE NO RESERVATIONS RIGHT NOW AS THIS LOGS AN EMPTY LIST
        callback(response.body())
    }
}