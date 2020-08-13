package com.halcyonmobile.rsrvd.makereservation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.halcyonmobile.rsrvd.MainActivity
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationRequestDto
import com.halcyonmobile.rsrvd.makereservation.MakeReservationViewModel

class ReservationSentActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_sent)
        val viewModel = ViewModelProviders.of(this).get(MakeReservationViewModel::class.java)
        val requestDto: ReservationRequestDto? = intent.getParcelableExtra<ReservationRequestDto>(RESERVATION_REQUEST)
        val id: String? = intent.getStringExtra(VENUE_ID)

        requestDto?.let {
            viewModel.makeReservation(
                requestDto.activityId,
                requestDto.start,
                requestDto.end,
                onSuccess = {
                    startActivity(MainActivity.instanceAfterReservation(this, MainActivity.MESSAGE_OK))
                    finish()
                },
                onFailure = {
                    id?.let {
                        startActivity(MakeReservationActivity.getStartIntent(this, it))
                        finish()
                    }
                }
            )
        }
    }

    companion object {
        private const val RESERVATION_REQUEST = "RESERVATION_REQUEST"
        private const val VENUE_ID = "VENUE_ID"

        fun getStartIntent(context: Context, reservationRequestDto: ReservationRequestDto, venueId: String) =
            Intent(context, ReservationSentActivity::class.java).apply {
                putExtra(RESERVATION_REQUEST, reservationRequestDto)
                putExtra(VENUE_ID, venueId)
            }

    }
}