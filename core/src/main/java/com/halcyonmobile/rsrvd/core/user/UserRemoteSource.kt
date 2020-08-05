package com.halcyonmobile.rsrvd.core.user

import android.util.Log
import com.halcyonmobile.rsrvd.core.shared.RetrofitSingleton
import com.halcyonmobile.rsrvd.core.user.dto.UserResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteSource {
    private val meApi = RetrofitSingleton.get().create(UserAPI::class.java)

    fun get(updateState: (UserResponseDto?) -> Unit) {
        meApi.get().enqueue(object : Callback<UserResponseDto> {
            override fun onResponse(call: Call<UserResponseDto>, response: Response<UserResponseDto>) {
                Log.d("RESPONSE", response.body().toString())
                updateState(response.body())
            }

            override fun onFailure(call: Call<UserResponseDto>, t: Throwable) {
                updateState(null)
            }
        })
    }
}