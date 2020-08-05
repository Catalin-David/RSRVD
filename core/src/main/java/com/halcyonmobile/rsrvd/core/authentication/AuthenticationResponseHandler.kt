package com.halcyonmobile.rsrvd.core.authentication

import android.content.ContentValues
import android.util.Log
import com.halcyonmobile.rsrvd.core.authentication.dto.AuthenticationResponseDto
import retrofit2.Call
import retrofit2.Callback

internal class AuthenticationResponseHandler(private val onSuccess: (token: String) -> Unit, private val onFailure: () -> Unit) : Callback<AuthenticationResponseDto> {
    override fun onResponse(call: Call<AuthenticationResponseDto>, response: retrofit2.Response<AuthenticationResponseDto>) {
        val result = response.body()

        if (!response.isSuccessful || result == null) {
            onFailure()
        } else {
            onSuccess(result.accessToken)

            Log.w(
                ContentValues.TAG,
                "code ${response.code()} ${result.accessToken}"
            )
        }
    }

    override fun onFailure(call: Call<AuthenticationResponseDto>, t: Throwable) {
        Log.w(ContentValues.TAG, "error: ", t)
        onFailure()
    }
}
