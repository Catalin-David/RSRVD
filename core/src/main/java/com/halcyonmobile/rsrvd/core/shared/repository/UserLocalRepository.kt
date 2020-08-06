package com.halcyonmobile.rsrvd.core.shared.repository

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
    var accessToken: String?
        get() = SharedPreferencesManager.accessToken
        set(token) {
            SharedPreferencesManager.accessToken = token
        }
    var exploreFirst: Boolean
        get() = SharedPreferencesManager.exploreFirst
        set(explore) {
            SharedPreferencesManager.exploreFirst = explore
        }
}