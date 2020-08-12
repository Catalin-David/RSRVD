package com.halcyonmobile.rsrvd.reservationdetails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.halcyonmobile.rsrvd.databinding.ActivityReservationDetailsBinding
import com.halcyonmobile.rsrvd.reservation.ReservationObjectFragment.Companion.LIST_HAS_CHANGED
import com.halcyonmobile.rsrvd.reservation.ReservationObjectFragment.Companion.NO_CHANGES

class ReservationDetailsActivity : AppCompatActivity() {
    private val viewModel: ReservationDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityReservationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reservationId = intent.getStringExtra(RESERVATION_ID_KEY)
        reservationId?.let {
            viewModel.loadReservation(it)
        }

        binding.apply {
            detailsViewModel = viewModel
            lifecycleOwner = this@ReservationDetailsActivity
            buttonCancelReservation.setOnClickListener {
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

    companion object {
        const val RESERVATION_ID_KEY = "com.halcyonmobile.rsrvd.reservation.RESERVATION_ID_KEY"

        fun getStartIntent(context: Context, reservationId: String) = Intent(context, ReservationDetailsActivity::class.java).apply {
            putExtra(RESERVATION_ID_KEY, reservationId)
        }
    }
}