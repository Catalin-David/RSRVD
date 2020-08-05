package com.halcyonmobile.rsrvd.signin

import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.shared.repository.LocalUserRepository

class SignInViewModel : ViewModel() {

    fun onAuthenticationResult(idToken: String,
                               onSuccess: (token: String) -> Unit,
                               onFailure: () -> Unit
    ) = LocalUserRepository.userSignIn(idToken, onSuccess, onFailure)

}