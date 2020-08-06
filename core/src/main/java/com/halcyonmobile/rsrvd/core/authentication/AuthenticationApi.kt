package com.halcyonmobile.rsrvd.core.authentication

import com.halcyonmobile.rsrvd.core.authentication.dto.AuthenticationRequestDto
import com.halcyonmobile.rsrvd.core.authentication.dto.AuthenticationResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {
    @POST("auth/google")
    fun postToken(@Body authenticationRequestDto: AuthenticationRequestDto) : Call<AuthenticationResponseDto>
}
