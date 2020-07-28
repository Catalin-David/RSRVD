package com.halcyonmobile.rsrvd.core.api

import com.halcyonmobile.rsrvd.core.api.dto.ProfileDto
import com.halcyonmobile.rsrvd.core.api.dto.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AppService {
    @POST("me")
    fun update(@Body body: ProfileDto): Call<UserDto>
}
