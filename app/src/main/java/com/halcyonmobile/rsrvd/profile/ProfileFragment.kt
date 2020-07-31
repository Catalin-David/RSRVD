package com.halcyonmobile.rsrvd.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.repository.UserRepository
import com.halcyonmobile.rsrvd.editprofile.EditProfileActivity
import com.halcyonmobile.rsrvd.signin.SignInActivity

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
                            UserRepository.exploreFirst = false
                            startActivity(Intent(activity, SignInActivity::class.java))
                        }
                }
                setNegativeButton("Cancel") { _, _ -> }
                show()
            }
        }

        view.findViewById<Button>(R.id.edit_profile).setOnClickListener {
            startActivity(Intent(this.activity, EditProfileActivity::class.java))
        }
    }
}
