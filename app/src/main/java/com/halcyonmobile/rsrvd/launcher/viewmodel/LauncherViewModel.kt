package com.halcyonmobile.rsrvd.launcher

import androidx.lifecycle.ViewModel

class LauncherViewModel(val routeUserToNextActionCallback: (Boolean) -> Unit): ViewModel() {

    private fun isUserLoggedIn(): Boolean {
        // TODO: Obtain state of currently logged user from repository
        return true
    }

    fun routeUserToNextAction(){
        routeUserToNextActionCallback(isUserLoggedIn())
    }
}