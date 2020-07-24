package com.halcyonmobile.rsrvd.core.repository

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.halcyonmobile.rsrvd.core.sharedpreferences.SharedPreferencesManager

object UserRepository {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    fun getSharedPreferences(context: Context): UserRepository {
        if (!::sharedPreferencesManager.isInitialized) {
            synchronized(UserRepository::class.java) {
                if (!::sharedPreferencesManager.isInitialized) {
                    sharedPreferencesManager = SharedPreferencesManager.getInstance(context)
                }
            }
        }
        return this
    }

    var isUserLoggedIn: Boolean
        get() = sharedPreferencesManager.isUserLoggedIn
        set(userStatus){
            sharedPreferencesManager.isUserLoggedIn = userStatus
        }
}