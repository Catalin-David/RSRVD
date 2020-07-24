package com.iuliamariabirsan.core.repository

import android.content.ContentValues
import android.util.Log
import com.iuliamariabirsan.core.RetrofitSingleton
import com.iuliamariabirsan.core.api.AuthenticationAPI
import com.iuliamariabirsan.core.dto.AuthenticationDto
import com.iuliamariabirsan.core.dto.AuthenticationResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object UserRepository {

    fun userSignIn(idToken: String) =
        RetrofitSingleton.get()
            .create(AuthenticationAPI::class.java)
            .postToken(AuthenticationDto(idToken))
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
