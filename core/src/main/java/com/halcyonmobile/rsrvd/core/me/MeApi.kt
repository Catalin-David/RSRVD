package com.halcyonmobile.rsrvd.core.me

import com.halcyonmobile.rsrvd.core.me.dto.ProfileDto
import com.halcyonmobile.rsrvd.core.me.dto.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MeApi {
    @POST("me")
    fun update(@Body body: ProfileDto): Call<UserDto>
}
