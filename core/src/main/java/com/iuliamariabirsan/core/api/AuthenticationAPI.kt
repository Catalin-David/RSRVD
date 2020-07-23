package com.iuliamariabirsan.core.api

import com.iuliamariabirsan.core.dto.AuthenticationDto
import com.iuliamariabirsan.core.dto.AuthenticationResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationAPI {
    @POST("/auth/google")
    fun postToken(@Body authenticationDto: AuthenticationDto) : Call<AuthenticationResponseDto>
}