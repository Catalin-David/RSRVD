package com.halcyonmobile.rsrvd

import android.app.Application
import com.halcyonmobile.rsrvd.core.repository.UserRepository

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        UserRepository.init(applicationContext)
    }
}