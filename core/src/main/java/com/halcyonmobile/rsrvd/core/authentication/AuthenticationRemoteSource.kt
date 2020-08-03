package com.halcyonmobile.rsrvd.core.authentication

import com.halcyonmobile.rsrvd.core.authentication.dto.AuthenticationRequestDto
import com.halcyonmobile.rsrvd.core.shared.RetrofitManager
import com.halcyonmobile.rsrvd.core.shared.interceptors.Interceptors

class AuthenticationRemoteSource {
    private val authenticationApi = RetrofitManager.getWithInterceptors(listOf(Interceptors.ContentType)).create(AuthenticationApi::class.java)

    fun postToken(idToken: String, onSuccess: (token: String) -> Unit, onFailure: () -> Unit) {
        authenticationApi.postToken(AuthenticationRequestDto(idToken)).enqueue(AuthenticationResponseHandler(onSuccess, onFailure))
    }
}