package com.halcyonmobile.rsrvd

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.halcyonmobile.rsrvd.feature.editprofile.EditProfileActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, EditProfileActivity::class.java))
    }
}