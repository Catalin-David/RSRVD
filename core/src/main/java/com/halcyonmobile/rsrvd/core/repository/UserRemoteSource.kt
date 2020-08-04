package com.halcyonmobile.rsrvd.core.repository

import android.util.Log
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
    fun getSignedInUserInformation(onRequestSuccess: (userProfileData: UserResponseDto?) -> Unit){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://39cutl7qwd.execute-api.eu-central-1.amazonaws.com/development/")
            .client(
                OkHttpClient.Builder().apply {
                    interceptors()
                        .add(Interceptor { chain ->
                            val newRequest: Request =
                                chain.request()
                                    .newBuilder()
                                    .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyX2NrZDVyMXpsZzAwMDAwN21nNWY3OTVlZzAiLCJpYXQiOjE1OTYwMjU5NjAsImV4cCI6MTU5NjExMjM2MCwiYXVkIjoiYnJhbWJsZTphY2Nlc3MifQ.Q8kvIWJnb6-SoYO4WOukU1v-r34XJ8k0DtK7Ia6naHYHyeKnMkKU_6ePFA5HJbBh9Bf1wNW1RIR8BcKd1BbaQlYTMYg8x9Y-ARxjQ6K9MEuFGcYodBftgRp7LadiRVyyhKXgocUn4Q0axb2L23WyA8DwSC9b3bYbuerA-xdL6vs")
                                    .build()
                            chain.proceed(newRequest)
                        })
                }
                .build()
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        retrofit.create(UserAPI::class.java).getCurrentlySignedInUser().enqueue(object: Callback<UserResponseDto>{
            override fun onFailure(call: Call<UserResponseDto>, t: Throwable) {
                Log.d("USER REMOTE SOURCE", "REQUEST FAILED")
                Log.d("USER REMOTE SOURCE", t.message ?: "Null message")
            }

            override fun onResponse(call: Call<UserResponseDto>, response: Response<UserResponseDto>) {
                Log.d("USERREMOTE", response.body().toString())
                onRequestSuccess(response.body())
            }
        })
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