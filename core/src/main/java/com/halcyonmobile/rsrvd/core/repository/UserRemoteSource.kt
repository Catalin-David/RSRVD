package com.halcyonmobile.rsrvd.core.repository

import android.util.Log
import com.halcyonmobile.rsrvd.core.HeaderInterceptor
import com.halcyonmobile.rsrvd.core.RetrofitSingleton
import com.halcyonmobile.rsrvd.core.api.UserAPI
import com.halcyonmobile.rsrvd.core.dto.UserResponseDto
import com.halcyonmobile.rsrvd.core.model.UserProfileData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class UserRemoteSource {
    fun getSignedInUserInformation(onRequestSuccess: (userProfileData: UserResponseDto?) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://39cutl7qwd.execute-api.eu-central-1.amazonaws.com/development/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HeaderInterceptor())
                    .build()
            )
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()

        val api = retrofit.create(UserAPI::class.java)
        api.getCurrentlySignedInUser().enqueue(object : Callback<UserResponseDto> {
            override fun onFailure(call: Call<UserResponseDto>, t: Throwable) {
                Log.d("USER REMOTE SOURCE", "REQUEST FAILED")
                Log.d("USER REMOTE SOURCE", t.message ?: "Null message")
            }

            override fun onResponse(call: Call<UserResponseDto>, response: Response<UserResponseDto>) {
                Log.d("USERREMOTE", response.body().toString())
                onRequestSuccess(response.body())
            }
        })
//      JUST ANOTHER WAY I THOUGHT OF DOING THIS REQUEST. SO FAR IT DOESN'T WORK EITHER WAY :(
//        RetrofitSingleton.get()
//            .create(UserAPI::class.java)
//            .getCurrentlySignedInUser()
//            .enqueue(object : Callback<UserResponseDto>{
//                override fun onFailure(call: Call<UserResponseDto>, t: Throwable) {
//                    Log.d("USER_REMOTE_SOURCE", "Request failed")
//                }
//
//                override fun onResponse(call: Call<UserResponseDto>, response: Response<UserResponseDto>) {
//                    onRequestSuccess(response.body())
//                }
//            })
    }
}