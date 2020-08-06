package com.halcyonmobile.rsrvd.signin

import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.authentication.AuthenticationRepository

class SignInViewModel : ViewModel() {
    fun onAuthenticationResult(
        idToken: String,
        onSuccess: (token: String) -> Unit,
        onFailure: () -> Unit
    ) = AuthenticationRepository.postToken(idToken, onSuccess, onFailure)
}