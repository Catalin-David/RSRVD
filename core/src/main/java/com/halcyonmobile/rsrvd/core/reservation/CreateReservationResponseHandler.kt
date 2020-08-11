package com.halcyonmobile.rsrvd.core.reservation

import android.content.ContentValues
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class CreateReservationResponseHandler(
    private val onSuccess: () -> Unit,
    private val onFailure: () -> Unit
) : Callback<Unit> {
    override fun onFailure(call: Call<Unit>, t: Throwable) {
        onSuccess()
    }

    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
        Log.d(ContentValues.TAG, "${response.code()}")
        onFailure()
    }
}
