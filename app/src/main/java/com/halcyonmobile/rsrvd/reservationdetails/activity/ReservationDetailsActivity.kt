package com.halcyonmobile.rsrvd.reservationdetails.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.halcyonmobile.rsrvd.databinding.ActivityReservationDetailsBinding
import com.halcyonmobile.rsrvd.reservation.ReservationObjectFragment.Companion.LIST_HAS_CHANGED
import com.halcyonmobile.rsrvd.reservation.ReservationObjectFragment.Companion.NO_CHANGES
import com.halcyonmobile.rsrvd.reservation.ReservationObjectFragment.Companion.RESERVATION_ID_KEY
import com.halcyonmobile.rsrvd.reservationdetails.viewmodel.ReservationDetailsViewModel

class ReservationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationDetailsBinding
    private lateinit var viewModel: ReservationDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(ReservationDetailsViewModel::class.java)

        val reservationId = intent.getStringExtra(RESERVATION_ID_KEY)
        reservationId?.let {
            viewModel.loadReservation(it)
        }

        viewModel.reservation.observe(this@ReservationDetailsActivity) { binding.reservation = it }

        binding.buttonCancelReservation.setOnClickListener {
            if (null != reservationId) {
                viewModel.cancelReservation(reservationId)
                setResult(LIST_HAS_CHANGED, Intent())
            } else {
                setResult(NO_CHANGES, Intent())
            }
            finish()
        }
    }
}