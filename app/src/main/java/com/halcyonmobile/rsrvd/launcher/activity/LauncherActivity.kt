package com.halcyonmobile.rsrvd.launcher.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.launcher.viewmodel.LauncherViewModel

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(LauncherViewModel::class.java)

        routeUserToNextAction(viewModel.isUserLoggedIn())

        finish()
    }

    private fun routeUserToNextAction(isUserLogged: Boolean) {
        if (isUserLogged) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // TODO: send to sign up activity
        }
    }
}