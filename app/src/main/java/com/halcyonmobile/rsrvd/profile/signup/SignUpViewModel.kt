package com.halcyonmobile.rsrvd.profile.signup

import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.repository.UserRepository

class SignUpViewModel : ViewModel() {

    fun onAuthenticationResult(idToken: String) {
        UserRepository.isUserLoggedIn = true
        UserRepository.userSignIn(idToken)
    }

}