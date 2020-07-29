package com.halcyonmobile.rsrvd.profile

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.signin.SignInActivity
import kotlin.system.exitProcess

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.logout).setOnClickListener {
            AlertDialog.Builder(view.context).apply {
                setTitle("Are you sure you want to log out?")
                setPositiveButton("Log out") { _, _ ->
                    GoogleSignIn.getClient(
                        context,
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                    )
                        .signOut()
                        .addOnCompleteListener {
                            UserRepository.isUserLoggedIn = false
                            startActivity(Intent(activity, SignInActivity::class.java))
                        }
                }
                setNegativeButton("Cancel") { _, _ -> }
                show()
            }
        }
    }

}