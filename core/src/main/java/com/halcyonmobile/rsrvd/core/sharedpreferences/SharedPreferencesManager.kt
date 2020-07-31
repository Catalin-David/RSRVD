package com.halcyonmobile.rsrvd.core.sharedpreferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import java.lang.Long.getLong

object SharedPreferencesManager {
    private const val IS_USER_LOGGED_IN_KEY = "com.halcyonmobile.rsrvd.IS_USER_LOGGED_IN_KEY"
    private const val EXPLORE_FIRST_KEY = "com.halcyonmobile.rsrvd.EXPLORE_FIRST_KEY"
    private const val ACCESS_TOKEN = "com.halcyonmobile.rsrvd.ACCESS_TOKEN"
    private const val LOCATION_LAT = "com.halcyonmobile.rsrvd.LOCATION_LAT"
    private const val LOCATION_LNG = "com.halcyonmobile.rsrvd.LOCATION_LNG"

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
    var accessToken: String?
        get() = sharedPreferences.getString(ACCESS_TOKEN, "")
        set(token) = with(sharedPreferences.edit()) {
            putString(ACCESS_TOKEN, token)
            apply()
        }
    var location: Pair<Double, Double>
        get() = Pair(
            java.lang.Double.longBitsToDouble(sharedPreferences.getLong(LOCATION_LAT, 0)),
            java.lang.Double.longBitsToDouble(sharedPreferences.getLong(LOCATION_LNG, 0))
        )
        set(newLocation) = with(sharedPreferences.edit()) {
            putLong(LOCATION_LAT, java.lang.Double.doubleToRawLongBits(newLocation.first))
            apply()
            putLong(LOCATION_LNG, java.lang.Double.doubleToRawLongBits(newLocation.second))
            apply()
        }
}