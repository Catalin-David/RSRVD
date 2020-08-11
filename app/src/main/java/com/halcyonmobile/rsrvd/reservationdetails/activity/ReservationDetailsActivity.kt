package com.halcyonmobile.rsrvd.reservationdetails.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.halcyonmobile.rsrvd.databinding.ActivityReservationDetailsBinding
import com.halcyonmobile.rsrvd.reservationdetails.viewmodel.ReservationDetailsViewModel

class ReservationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationDetailsBinding
    private lateinit var viewModel: ReservationDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(ReservationDetailsViewModel::class.java)

        intent.getStringExtra(RESERVATION_ID_KEY)?.let {
            viewModel.loadReservation(it)
        }

        viewModel.apply {
            reservation.observe(this@ReservationDetailsActivity){binding.reservation = it}
        }

        binding.buttonCancelReservation.setOnClickListener {
            // make api request
            finish()
        }
    }

    companion object{
        const val RESERVATION_ID_KEY = "com.halcyonmobile.rsrvd.RESERVATION_ID_KEY"
    }
}