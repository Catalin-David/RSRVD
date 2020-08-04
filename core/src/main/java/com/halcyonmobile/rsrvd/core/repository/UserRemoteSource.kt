package com.halcyonmobile.rsrvd.core.repository

import com.halcyonmobile.rsrvd.core.RetrofitSingleton
import com.halcyonmobile.rsrvd.core.api.UserAPI
import com.halcyonmobile.rsrvd.core.dto.UserResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteSource {
    fun getSignedInUserInformation(): UserResponseDto? {
        var result: UserResponseDto? = null
        RetrofitSingleton.get()
            .create(UserAPI::class.java)
            .getCurrentlySignedInUser()
            .enqueue(object : Callback<UserResponseDto>{
                override fun onFailure(call: Call<UserResponseDto>, t: Throwable) {
                    result = null
                }

                override fun onResponse(call: Call<UserResponseDto>, response: Response<UserResponseDto>) {
                    result = response.body()
                }
            })
        return result
    }
}