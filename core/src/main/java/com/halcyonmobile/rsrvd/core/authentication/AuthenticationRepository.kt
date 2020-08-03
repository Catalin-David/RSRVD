package com.halcyonmobile.rsrvd.core.authentication

class AuthenticationRepository {
    private val authenticationRemoteSource = AuthenticationRemoteSource()

    fun postToken(idToken: String, onSuccess: (token: String) -> Unit, onFailure: () -> Unit) {
        authenticationRemoteSource.postToken(idToken, onSuccess, onFailure)
    }
}