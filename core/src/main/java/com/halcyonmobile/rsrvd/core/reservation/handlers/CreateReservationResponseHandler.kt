package com.halcyonmobile.rsrvd.core.reservation.handlers

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class CreateReservationResponseHandler(
    private val onSuccess: () -> Unit,
    private val onFailure: () -> Unit
) : Callback<Unit> {
    override fun onFailure(call: Call<Unit>, t: Throwable) {
        onFailure()
    }

    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
        if (response.code() == 400) {
            onFailure()
        } else {
            onSuccess()
        }
    }
}
