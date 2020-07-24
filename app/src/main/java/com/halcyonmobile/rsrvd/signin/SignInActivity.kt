package com.halcyonmobile.rsrvd.signin

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.ActivitySignInBinding
import com.iuliamariabirsan.core.repository.UserRepository


class SignInActivity : AppCompatActivity() {

    private lateinit var signInBinding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        signInBinding.singInViewModel =
            SignInViewModel()

        findViewById<TextView>(R.id.expl_rsrvd_live_first_text_view).startAnimation(
            AnimationUtils.loadAnimation(
            this, R.anim.slide_to_right
        ));

        findViewById<Button>(R.id.connect_google_button).setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if( account == null ) {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(this.getString(R.string.client_id))
                    .requestEmail()
                    .build()

            val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(this, gso)

            startActivityForResult(mGoogleSignInClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GOOGLE_SIGN_IN) {
            val idToken = GoogleSignIn
                .getSignedInAccountFromIntent(data)
                .getResult(ApiException::class.java)!!.idToken

            if (idToken != null) {
                UserRepository.userSignIn(idToken)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val GOOGLE_SIGN_IN = 19
    }
}