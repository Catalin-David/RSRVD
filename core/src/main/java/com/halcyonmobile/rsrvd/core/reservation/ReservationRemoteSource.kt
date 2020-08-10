package com.halcyonmobile.rsrvd.core.reservation

import android.util.Log
import com.halcyonmobile.rsrvd.core.reservation.api.ReservationApi
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import com.halcyonmobile.rsrvd.core.shared.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationRemoteSource {
    private val reservationApi = RetrofitManager.retrofitWithAuthentication!!.create(ReservationApi::class.java)

    fun get(updateState: (List<ReservationDto>?) -> Unit) {
        reservationApi.get().enqueue(object : Callback<List<ReservationDto>> {
            override fun onFailure(call: Call<List<ReservationDto>>, t: Throwable) {
                updateState(listOf())
                Log.d("RESERVATION REMOTE SRC", t.message ?: "NO MESSAGE")
            }

            override fun onResponse(call: Call<List<ReservationDto>>, response: Response<List<ReservationDto>>) {
                Log.d("TAG", response.body().toString()) // LOOKS LIKE THERE ARE NO RESERVATIONS RIGHT NOW AS THIS LOGS AN EMPTY LIST
                updateState(response.body())
            }
        })
    }
}