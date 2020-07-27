package com.halcyonmobile.rsrvd

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.halcyonmobile.rsrvd.feature.editprofile.EditProfileActivity
import com.halcyonmobile.rsrvd.feature.onboarding.OnboardingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivityForResult(Intent(this, EditProfileActivity::class.java), EDIT_PROFILE_REQUEST_CODE)
//        startActivity(Intent(this, OnboardingActivity::class.java))
    }

    companion object {
        private const val EDIT_PROFILE_REQUEST_CODE = 1
    }
}