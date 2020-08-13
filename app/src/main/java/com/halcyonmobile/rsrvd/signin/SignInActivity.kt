package com.halcyonmobile.rsrvd.signin

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.shared.repository.UserLocalRepository
import com.halcyonmobile.rsrvd.databinding.ActivitySignInBinding
import com.halcyonmobile.rsrvd.onboarding.OnboardingActivity
import com.halcyonmobile.rsrvd.utils.showSnackbar

class SignInActivity : AppCompatActivity() {
    private lateinit var signInBinding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)

        if (intent.getBooleanExtra(SIGN_UP_KEY, false)) {
            signInBinding.exploreFirst.visibility = View.GONE
            signInBinding.welcomeToRsrvdTextView.text = getString(R.string.create)
            signInBinding.rsrvdTextView.text = getString(R.string.account)
            signInBinding.closeSignUp.visibility = View.VISIBLE
        }

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

        signInBinding.closeSignUp.setOnClickListener {
            startActivity(MainActivity.instanceAfterReservation(this, getString(R.string.cancel)))
        }
    }

    private fun signIn() {
        if (GoogleSignIn.getLastSignedInAccount(this) == null) {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.client_id))
                    .requestEmail()
                    .build()

            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            startActivityForResult(mGoogleSignInClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    private fun exploreFirst() {
        UserLocalRepository.exploreFirst = true
        UserLocalRepository.accessToken = ""
        UserLocalRepository.location = Pair(0.0, 0.0)
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GOOGLE_SIGN_IN) {
            try {
                signInBinding.signInProgress.visibility = View.VISIBLE
                GoogleSignIn
                    .getSignedInAccountFromIntent(data)
                    .getResult(ApiException::class.java)?.idToken?.let {
                        Log.w(ContentValues.TAG, "code $it")

                        viewModel.onAuthenticationResult(it,
                            onSuccess = { accessToken ->
                                UserLocalRepository.accessToken = accessToken
                                Log.w(ContentValues.TAG, "access token $accessToken")
                                UserLocalRepository.isUserLoggedIn = true
                                signInBinding.signInProgress.visibility = View.INVISIBLE
                                startActivity(Intent(this, OnboardingActivity::class.java))
                            },
                            onFailure = {
                                UserLocalRepository.accessToken = ""
                                UserLocalRepository.isUserLoggedIn = false
                                UserLocalRepository.location = Pair(0.0, 0.0)
                                signInBinding.root.showSnackbar(getString(R.string.authentication_failed))
                                signInBinding.signInProgress.visibility = View.INVISIBLE
                            })
                    }
            } catch (e: ApiException) {
                UserLocalRepository.accessToken = ""
                UserLocalRepository.location = Pair(0.0, 0.0)
                signInBinding.root.showSnackbar(getString(R.string.authentication_failed))
                signInBinding.signInProgress.visibility = View.INVISIBLE
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val GOOGLE_SIGN_IN = 19
        private const val SIGN_UP_KEY = "SIGN_UP_KEY"

        fun getStartIntent(context: Context, showSignUp: Boolean) =
            Intent(context, SignInActivity::class.java).putExtra(SIGN_UP_KEY, showSignUp)
        
        fun instanceStackClear(context: Context?): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            return intent
        }
    }
}