package com.halcyonmobile.rsrvd.launcher.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LauncherViewModelFactory(val arg: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = modelClass.getConstructor(Application::class.java).newInstance(arg)
}