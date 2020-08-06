package com.halcyonmobile.rsrvd.core.authentication

import com.halcyonmobile.rsrvd.core.authentication.dto.AuthenticationRequestDto
import com.halcyonmobile.rsrvd.core.shared.RetrofitManager

class AuthenticationRemoteSource {
    private val authenticationApi = RetrofitManager.retrofit!!.create(AuthenticationApi::class.java)

    fun postToken(idToken: String, onSuccess: (token: String) -> Unit, onFailure: () -> Unit) {
        authenticationApi.postToken(AuthenticationRequestDto(idToken)).enqueue(AuthenticationResponseHandler(onSuccess, onFailure))
    }
}