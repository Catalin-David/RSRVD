package com.halcyonmobile.rsrvd.launcher.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.iuliamariabirsan.core.repository.UserRepository

class LauncherViewModel(application: Application) : AndroidViewModel(application) {
    fun isUserLoggedIn(): Boolean = UserRepository.getInstance(getApplication()).isUserLoggedIn
}