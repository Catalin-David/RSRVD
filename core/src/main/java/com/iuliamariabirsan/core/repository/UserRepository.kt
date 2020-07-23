package com.iuliamariabirsan.core.repository

import android.content.ContentValues
import android.util.Log
import com.iuliamariabirsan.core.api.AuthenticationAPI
import com.iuliamariabirsan.core.api.HttpClientFactoryAuth
import com.iuliamariabirsan.core.dto.AuthenticationDto
import com.iuliamariabirsan.core.dto.AuthenticationResponseDto
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object UserRepository {
    fun userSignIn(userToken :String) {
        val authenticationDto = AuthenticationDto()
        authenticationDto.idToken = userToken

        Retrofit.Builder()
            .baseUrl("https://app.swaggerhub.com/")
            .client(HttpClientFactoryAuth.getClient())
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
            .create(AuthenticationAPI::class.java)
            .postToken(authenticationDto)
            .enqueue(object : Callback<AuthenticationResponseDto>{
                override fun onFailure(call: Call<AuthenticationResponseDto>, t: Throwable) {
                    Log.w(ContentValues.TAG, "to", t)
                }

                override fun onResponse(
                    call: Call<AuthenticationResponseDto>,
                    response: Response<AuthenticationResponseDto>
                ) {
                    Log.w(ContentValues.TAG, "token ${response.code()}")
                }

            })
    }

}
