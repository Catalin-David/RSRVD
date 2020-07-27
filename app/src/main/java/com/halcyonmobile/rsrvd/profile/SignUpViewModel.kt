package com.halcyonmobile.rsrvd.profile

import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.repository.UserRepository

class SignUpViewModel : ViewModel() {

    fun onAuthenticationResult(idToken: String)  = UserRepository.userSignIn(idToken)

}