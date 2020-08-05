package com.halcyonmobile.rsrvd.launcher.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.halcyonmobile.rsrvd.core.shared.repository.UserRepository

class LauncherViewModel(application: Application) : AndroidViewModel(application) {
    fun isUserLoggedIn(): Boolean = UserRepository.isUserLoggedIn
}