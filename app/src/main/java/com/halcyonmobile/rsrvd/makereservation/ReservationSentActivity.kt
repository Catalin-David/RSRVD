package com.halcyonmobile.rsrvd.makereservation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.R

class ReservationSentActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_sent)

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))

            finish()
        }, SPLASH_TIME_OUT)
    }

    companion object {
        private const val SPLASH_TIME_OUT : Long = 3000
    }

}