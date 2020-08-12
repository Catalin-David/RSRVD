package com.halcyonmobile.rsrvd.core.reservation.handlers

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CancelReservationHandler : Callback<Any> {
    override fun onFailure(call: Call<Any>, t: Throwable) {
        Log.d("CANCEL_CALLBACK_FAILED", t.message ?: "no error message found")
    }

    override fun onResponse(call: Call<Any>, response: Response<Any>) {
        Log.d("CANCEL_CALLBACK_SUCCESS", "${response.message()} ${response.body().toString()}")
    }
}