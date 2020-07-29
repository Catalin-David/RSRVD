package com.halcyonmobile.rsrvd.launcher.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.launcher.viewmodel.LauncherViewModel
import com.halcyonmobile.rsrvd.launcher.viewmodel.LauncherViewModelFactory

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        routeUserToNextAction()

        finish()
    }

    private fun routeUserToNextAction() {
        val viewModel = ViewModelProvider(this, LauncherViewModelFactory(application))
        val isUserLogged = true // viewModel.get(LauncherViewModel::class.java).isUserLoggedIn()
        if (isUserLogged) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Log.d("TAG: ", "USER NOT LOGGED IN")
            // TODO: send user to Sign up activity
        }
    }
}