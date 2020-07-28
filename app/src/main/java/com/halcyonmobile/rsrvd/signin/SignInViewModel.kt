package com.halcyonmobile.rsrvd.signin

import androidx.lifecycle.ViewModel
import com.iuliamariabirsan.core.repository.UserRepository

class SignInViewModel : ViewModel() {

    fun onAuthenticationResult(idToken: String) = UserRepository.userSignIn(idToken)

}