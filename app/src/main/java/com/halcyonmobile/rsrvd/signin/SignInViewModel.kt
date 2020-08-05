package com.halcyonmobile.rsrvd.signin

import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.shared.repository.UserRepository

class SignInViewModel : ViewModel() {

    fun onAuthenticationResult(idToken: String,
                               onSuccess: (token: String) -> Unit,
                               onFailure: () -> Unit
    ) = UserRepository.userSignIn(idToken, onSuccess, onFailure)

}