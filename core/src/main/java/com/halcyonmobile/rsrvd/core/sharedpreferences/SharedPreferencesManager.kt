package com.halcyonmobile.rsrvd.core.sharedpreferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val IS_USER_LOGGED_IN_KEY = "com.halcyonmobile.rsrvd.IS_USER_LOGGED_IN_KEY"

    private lateinit var sharedPreferences: SharedPreferences

    fun getInstance(context: Context): SharedPreferencesManager {
        if(!::sharedPreferences.isInitialized){
            synchronized(SharedPreferencesManager::class.java) {
                if (!::sharedPreferences.isInitialized) {
                    sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                }
            }
        }
        return this
    }

    var isUserLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(IS_USER_LOGGED_IN_KEY, false)
        set(userStatus) {
            val editor = sharedPreferences.edit()
            with(editor) {
                putBoolean(IS_USER_LOGGED_IN_KEY, userStatus)
                apply()
            }
        }
}