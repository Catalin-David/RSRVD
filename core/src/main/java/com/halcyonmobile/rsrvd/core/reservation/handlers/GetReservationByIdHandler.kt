package com.halcyonmobile.rsrvd.core.reservation.handlers

import android.util.Log
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetReservationByIdHandler(private val callback: (ReservationDto?) -> Unit): Callback<ReservationDto> {
    override fun onFailure(call: Call<ReservationDto>, t: Throwable){
        Log.d("RESERVATION_BY_ID", "request failed: ${t.message}")
        callback(null)
    }


    override fun onResponse(call: Call<ReservationDto>, response: Response<ReservationDto>) {
        Log.d("RESERVATION_BY_ID", "request success: ${response.message()}\n${response.body().toString()}")
        callback(response.body())
    }
}