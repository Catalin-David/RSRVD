package com.halcyonmobile.rsrvd.launcher.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.launcher.viewmodel.LauncherViewModel
import com.halcyonmobile.rsrvd.launcher.viewmodel.LauncherViewModelFactory
import com.halcyonmobile.rsrvd.signin.SignInActivity

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        routeUserToNextAction()

        finish()
    }

    private fun routeUserToNextAction() {
        val viewModel = ViewModelProvider(this, LauncherViewModelFactory(application))
        val isUserLogged = viewModel.get(LauncherViewModel::class.java).isUserLoggedIn()
        if (isUserLogged) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}