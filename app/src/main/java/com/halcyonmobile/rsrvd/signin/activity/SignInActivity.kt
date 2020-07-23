package com.halcyonmobile.rsrvd.signin.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.ActivitySignInBinding
import com.halcyonmobile.rsrvd.signin.event.ActivityNavigation
import com.halcyonmobile.rsrvd.signin.viewmodel.SignInViewModel


class SignInActivity : AppCompatActivity(), ActivityNavigation {

    private lateinit var signInBinding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        signInBinding.singInViewModel = SignInViewModel(this)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if( account == null ) {
            subscribeUi()
        }
    }

    private fun subscribeUi() {
        signInBinding.singInViewModel
            ?.startActivityForResultEvent
            ?.setEventReceiver(this, this)!!
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if( account == null ) {
            signInBinding.singInViewModel
                ?.onResultFromActivity(requestCode, resultCode, data)!!
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}