package com.halcyonmobile.rsrvd.profile.signup

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        signUpBinding.signUpViewModel =
            SignUpViewModel()

        findViewById<TextView>(R.id.expl_rsrvd_live_first_text_view_signup).startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.text_view_animation
            )
        )

        findViewById<Button>(R.id.connect_google_button_signup).setOnClickListener {
            signUp()
        }

    }

    private fun signUp() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        startActivityForResult(mGoogleSignInClient.signInIntent,
            GOOGLE_SIGN_UP
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GOOGLE_SIGN_UP) {
            GoogleSignIn
                .getSignedInAccountFromIntent(data)
                .getResult(ApiException::class.java)?.idToken.let {
                    if (it != null) {
                        signUpBinding.signUpViewModel?.onAuthenticationResult(it)
                    }
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    companion object {
        private const val GOOGLE_SIGN_UP = 17
    }

}
