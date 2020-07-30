package com.halcyonmobile.rsrvd.signin

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.databinding.ActivitySignInBinding
import com.halcyonmobile.rsrvd.onboarding.OnboardingActivity


class SignInActivity : AppCompatActivity() {

    private lateinit var signInBinding: ActivitySignInBinding
    private var viewModel = SignInViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        signInBinding.exploreFirst.isSelected = true

        signInBinding.explRsrvdLiveFirstTextView.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.text_view_animation
            )
        )

        signInBinding.connectGoogleButton.setOnClickListener {
            signIn()
        }

        signInBinding.exploreFirst.setOnClickListener {
            exploreFirst()
        }
    }

    private fun signIn() {
        if ( GoogleSignIn.getLastSignedInAccount(this) == null ) {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.client_id))
                    .requestEmail()
                    .build()

            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            startActivityForResult(mGoogleSignInClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    private fun exploreFirst () {
        //TO DO: create another variable in repo
       // UserRepository.isUserLoggedIn = false
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GOOGLE_SIGN_IN) {
            try {
                GoogleSignIn
                    .getSignedInAccountFromIntent(data)
                    .getResult(ApiException::class.java)?.idToken?.let {
                        Log.w(ContentValues.TAG, "code $it")
                        signInBinding.signInProgress.visibility = View.VISIBLE

                        viewModel.onAuthenticationResult(it,
                        onSuccess = { accessToken ->
                            // TODO: assign the access token
                            Log.w(ContentValues.TAG, "access token $accessToken")
                            startActivity(Intent(this, OnboardingActivity::class.java))
                            UserRepository.isUserLoggedIn = true
                            signInBinding.signInProgress.visibility = View.INVISIBLE
                        },
                        onFailure = {
                            Snackbar.make(signInBinding.layoutSignIn,
                                getString(R.string.authentication_failed),
                                Snackbar.LENGTH_SHORT).show()

                            UserRepository.isUserLoggedIn = false
                            signInBinding.signInProgress.visibility = View.INVISIBLE
                        })
                    }
            } catch (e: ApiException) {
                Snackbar.make(signInBinding.layoutSignIn,
                    getString(R.string.authentication_failed),
                    Snackbar.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val GOOGLE_SIGN_IN = 19
    }
}