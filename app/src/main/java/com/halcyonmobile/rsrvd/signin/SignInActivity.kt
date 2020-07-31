package com.halcyonmobile.rsrvd.signin

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.ActivitySignInBinding
import com.halcyonmobile.rsrvd.utils.showSnackbar


class SignInActivity : AppCompatActivity() {

    private lateinit var signInBinding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)

        if ( intent.getBooleanExtra(SIGN_UP_KEY, false) ) {
            signInBinding.exploreFirst.visibility = View.GONE
            signInBinding.welcomeToRsrvdTextView.text = getString(R.string.create)

            signInBinding.rsrvdTextView.text = getString(R.string.account)
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

    private fun exploreFirst () {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GOOGLE_SIGN_IN) {
            try {
                GoogleSignIn
                    .getSignedInAccountFromIntent(data)
                    .getResult(ApiException::class.java)?.idToken?.let {
                        Log.w(ContentValues.TAG, "code $it")

                        viewModel.onAuthenticationResult(it,
                        onSuccess = { accessToken ->
                            // TODO: assign the access token
                            Log.w(ContentValues.TAG, "access token $accessToken")
                        },
                        onFailure = {
                            signInBinding.root.showSnackbar(getString(R.string.authentication_failed))
                        })
                    }
            } catch (e: ApiException) {
                signInBinding.root.showSnackbar(getString(R.string.authentication_failed))
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val GOOGLE_SIGN_IN = 19
        private const val SIGN_UP_KEY = "SIGN_UP_KEY"

        fun getStartIntent(context: Context, data: Boolean) =
            Intent(context, SignInActivity::class.java).putExtra(SIGN_UP_KEY, data)
    }
}