package com.halcyonmobile.rsrvd.core.api

import com.halcyonmobile.rsrvd.core.dto.AuthenticationRequestDto
import com.halcyonmobile.rsrvd.core.dto.AuthenticationResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationAPI {
    @POST("auth/google")
    fun postToken(@Body authenticationRequestDto: AuthenticationRequestDto) : Call<AuthenticationResponseDto>
}