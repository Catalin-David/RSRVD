package com.halcyonmobile.rsrvd.core.shared.repository

import android.content.ContentValues
import android.util.Log
import com.halcyonmobile.rsrvd.core.authentication.AuthenticationApi
import com.halcyonmobile.rsrvd.core.authentication.AuthenticationRepository
import com.halcyonmobile.rsrvd.core.authentication.dto.AuthenticationRequestDto
import com.halcyonmobile.rsrvd.core.authentication.dto.AuthenticationResponseDto
import com.halcyonmobile.rsrvd.core.shared.RetrofitManager
import com.halcyonmobile.rsrvd.core.shared.interceptors.Interceptors
import com.halcyonmobile.rsrvd.core.shared.sharedpreferences.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {
    // TODO set when looged in
    var name: String = "NAME"

    // TODO change when selected
    var location: Pair<Double, Double> = Pair(0.0, 0.0)

    var isUserLoggedIn: Boolean
        get() = SharedPreferencesManager.isUserLoggedIn
        set(userStatus) {
            SharedPreferencesManager.isUserLoggedIn = userStatus
        }

    fun userSignIn(idToken: String, onSuccess: (token: String) -> Unit, onFailure: () -> Unit) =
        AuthenticationRepository().postToken(idToken, onSuccess, onFailure)
}