package com.halcyonmobile.rsrvd.core.shared.repository

import com.halcyonmobile.rsrvd.core.authentication.AuthenticationRepository
import com.halcyonmobile.rsrvd.core.shared.sharedpreferences.SharedPreferencesManager
import com.halcyonmobile.rsrvd.core.user.model.UserProfileData
import com.halcyonmobile.rsrvd.core.user.UserRemoteSource

object LocalUserRepository {
    private val userRemoteSource =
        UserRemoteSource()

    // TODO set when looged in
    var name: String = "NAME"
    var exploreFirst: Boolean
        get() = SharedPreferencesManager.exploreFirst
        set(exploreStatus) {
            SharedPreferencesManager.exploreFirst = exploreStatus
        }
    var accessToken: String?
        get() = SharedPreferencesManager.accessToken
        set(token) {
            SharedPreferencesManager.accessToken = token
        }

    var location: Pair<Double, Double>
        get() = SharedPreferencesManager.location
        set(newLocation) {
            SharedPreferencesManager.location = newLocation
        }

    var isUserLoggedIn: Boolean
        get() = SharedPreferencesManager.isUserLoggedIn
        set(userStatus) {
            SharedPreferencesManager.isUserLoggedIn = userStatus
        }

    fun userSignIn(idToken: String, onSuccess: (token: String) -> Unit, onFailure: () -> Unit) =
        AuthenticationRepository().postToken(idToken, onSuccess, onFailure)

    fun loadProfileData(onSuccess: (userProfileData: UserProfileData) -> Unit) =
        userRemoteSource.get { it?.let { onSuccess(
            UserProfileData(
                it.location,
                it.reservations,
                it.interests
            )
        ) } }
}