package com.halcyonmobile.rsrvd

import android.app.Application
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.core.sharedpreferences.SharedPreferencesManager

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        SharedPreferencesManager.init(applicationContext)
    }
}