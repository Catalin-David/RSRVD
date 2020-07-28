package com.halcyonmobile.rsrvd.profile.signup

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.repository.UserRepository

class SignUpViewModel : ViewModel() {

    fun onAuthenticationResult(idToken: String,
                               onSuccess: (token: String) -> Unit,
                               onFailure: () -> Unit
    ) = UserRepository.userSignIn(idToken, onSuccess, onFailure)

}