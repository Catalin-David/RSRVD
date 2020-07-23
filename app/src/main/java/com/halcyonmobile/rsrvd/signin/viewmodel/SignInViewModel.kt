package com.halcyonmobile.rsrvd.signin.viewmodel

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.signin.event.ActivityNavigation
import com.halcyonmobile.rsrvd.signin.event.LiveMessageEvent
import com.iuliamariabirsan.core.store.GoogleRepo


class SignInViewModel(private val context: Context) : ViewModel() {

    val startActivityForResultEvent = LiveMessageEvent<ActivityNavigation>()

    fun justExplore () {}

    fun googleConnect() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.resources.getString(R.string.client_id))
                .requestEmail()
                .build()

        val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

        startActivityForResultEvent.sendEvent {
            startActivityForResult(
                mGoogleSignInClient.signInIntent,
                GOOGLE_SIGN_IN
            )
        }
    }

    fun onResultFromActivity(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            GoogleRepo().handleSignInResult(task)
        }
    }

    companion object {
        private const val GOOGLE_SIGN_IN = 19
    }

}