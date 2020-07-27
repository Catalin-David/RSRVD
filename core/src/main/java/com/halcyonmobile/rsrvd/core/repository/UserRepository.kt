package com.halcyonmobile.rsrvd.core.repository

import android.content.ContentValues
import android.util.Log
import com.halcyonmobile.rsrvd.core.RetrofitSingleton
import com.halcyonmobile.rsrvd.core.api.AuthenticationAPI
import com.halcyonmobile.rsrvd.core.dto.AuthenticationRequestDto
import com.halcyonmobile.rsrvd.core.dto.AuthenticationResponseDto
import com.halcyonmobile.rsrvd.core.sharedpreferences.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {
    var isUserLoggedIn: Boolean
        get() = SharedPreferencesManager.isUserLoggedIn
        set(userStatus) {
            SharedPreferencesManager.isUserLoggedIn = userStatus
        }

    fun userSignIn(idToken: String) =
        RetrofitSingleton.get()
            .create(AuthenticationAPI::class.java)
            .postToken(AuthenticationRequestDto(idToken))
            .enqueue(object : Callback<AuthenticationResponseDto>{
                override fun onFailure(call: Call<AuthenticationResponseDto>, t: Throwable) {
                    Log.w(ContentValues.TAG, "error: ", t)
                }

                override fun onResponse(
                    call: Call<AuthenticationResponseDto>,
                    response: Response<AuthenticationResponseDto>
                ) {
                    Log.w(ContentValues.TAG, "token ${response.code()}")
                }
            })

}