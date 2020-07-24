package com.halcyonmobile.rsrvd.core.repository

import android.content.Context
import com.halcyonmobile.rsrvd.core.sharedpreferences.SharedPreferencesManager

object UserRepository {

    fun init(context: Context) {
        SharedPreferencesManager.init(context)
    }

    var isUserLoggedIn: Boolean
        get() = SharedPreferencesManager.isUserLoggedIn
        set(userStatus) {
            SharedPreferencesManager.isUserLoggedIn = userStatus
        }
}