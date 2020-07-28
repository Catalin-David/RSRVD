package com.halcyonmobile.rsrvd.profile.signup

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding
    private var viewModel = SignUpViewModel()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        signUpBinding.signUpViewModel =
            SignUpViewModel()

        signUpBinding.explRsrvdLiveFirstTextViewSignup.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.text_view_animation
            )
        )

        signUpBinding.connectGoogleButtonSignup.setOnClickListener {
            signUp()
        }

    }

    private fun signUp() {
        if ( GoogleSignIn.getLastSignedInAccount(this) == null ) {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.client_id))
                    .requestEmail()
                    .build()

            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            startActivityForResult(mGoogleSignInClient.signInIntent, GOOGLE_SIGN_UP)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GOOGLE_SIGN_UP) {
            try {
                GoogleSignIn
                    .getSignedInAccountFromIntent(data)
                    .getResult(ApiException::class.java)?.idToken?.let {
                        Log.w(ContentValues.TAG, "code $it")

                        viewModel.onAuthenticationResult(it,
                            onSuccess = { accessToken ->
                                //TO DO: assign the access token
                                Log.w(ContentValues.TAG, "access token $accessToken")
                            },
                            onFailure = {
                                Snackbar.make(signUpBinding.layoutSignUp,
                                    getString(R.string.authentication_failed),
                                    Snackbar.LENGTH_SHORT).show()
                            })
                    }
            } catch (e: ApiException) {
                Snackbar.make(signUpBinding.layoutSignUp,
                    getString(R.string.authentication_failed),
                    Snackbar.LENGTH_SHORT).show()
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    companion object {
        private const val GOOGLE_SIGN_UP = 17
    }

}
