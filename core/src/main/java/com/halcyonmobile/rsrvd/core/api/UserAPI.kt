package com.halcyonmobile.rsrvd.core.api

import com.halcyonmobile.rsrvd.core.dto.UserResponseDto
import retrofit2.Call
import retrofit2.http.GET

interface UserAPI {
    @GET("me")
    fun get(): Call<UserResponseDto>
}