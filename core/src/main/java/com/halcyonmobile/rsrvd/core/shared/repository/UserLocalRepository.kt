package com.halcyonmobile.rsrvd.core.shared.repository

import com.halcyonmobile.rsrvd.core.authentication.AuthenticationRepository
import com.halcyonmobile.rsrvd.core.shared.sharedpreferences.SharedPreferencesManager

object UserLocalRepository {
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