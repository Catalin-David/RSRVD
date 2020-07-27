package com.halcyonmobile.rsrvd.core.repository

import com.halcyonmobile.rsrvd.core.sharedpreferences.SharedPreferencesManager

object UserRepository {
    var isUserLoggedIn: Boolean
        get() = SharedPreferencesManager.isUserLoggedIn
        set(userStatus) {
            SharedPreferencesManager.isUserLoggedIn = userStatus
        }
}