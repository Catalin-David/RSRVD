package com.halcyonmobile.rsrvd.core.user

import com.halcyonmobile.rsrvd.core.user.dto.ProfileDto
import com.halcyonmobile.rsrvd.core.user.dto.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface UserApi {
    @POST("me")
    fun update(@Body body: ProfileDto): Call<UserDto>

    @GET("me")
    fun get(): Call<UserDto>
}
