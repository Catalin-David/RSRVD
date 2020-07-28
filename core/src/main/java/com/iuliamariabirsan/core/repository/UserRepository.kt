package com.iuliamariabirsan.core.repository

import android.content.ContentValues
import android.util.Log
import com.iuliamariabirsan.core.RetrofitSingleton
import com.iuliamariabirsan.core.api.AuthenticationAPI
import com.iuliamariabirsan.core.dto.AuthenticationRequestDto
import com.iuliamariabirsan.core.dto.AuthenticationResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

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
                    Log.w(ContentValues.TAG, "code ${response.code()}")
                }
            })

}
