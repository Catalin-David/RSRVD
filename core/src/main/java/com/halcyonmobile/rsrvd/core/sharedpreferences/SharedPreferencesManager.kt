package com.halcyonmobile.rsrvd.core.sharedpreferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val IS_USER_LOGGED_IN_KEY = "com.halcyonmobile.rsrvd.IS_USER_LOGGED_IN_KEY"
    private const val EXPLORE_FIRST_KEY = "com.halcyonmobile.rsrvd.EXPLORE_FIRST_KEY"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    var isUserLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(IS_USER_LOGGED_IN_KEY, false)
        set(userStatus) = with(sharedPreferences.edit()) {
            putBoolean(IS_USER_LOGGED_IN_KEY, userStatus)
            apply()
        }
    var exploreFirst: Boolean
        get() = sharedPreferences.getBoolean(EXPLORE_FIRST_KEY, false)
        set(userStatus) = with(sharedPreferences.edit()) {
            putBoolean(EXPLORE_FIRST_KEY, userStatus)
            apply()
        }
}