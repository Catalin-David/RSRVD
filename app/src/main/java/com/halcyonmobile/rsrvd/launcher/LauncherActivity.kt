package com.halcyonmobile.rsrvd.launcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.halcyonmobile.rsrvd.MainActivity

class LauncherActivity : AppCompatActivity() {

    private val viewModel = LauncherViewModel{isUserLogged -> routeUserToNextActionCallback(isUserLogged)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        viewModel.routeUserToNextAction()

        finish()
    }

    private fun routeUserToNextActionCallback(isUserLogged: Boolean){
        if(isUserLogged){
            startActivity(Intent(this, MainActivity::class.java))
        }
        else{
            // TODO: send to sign up activity
        }
    }
}