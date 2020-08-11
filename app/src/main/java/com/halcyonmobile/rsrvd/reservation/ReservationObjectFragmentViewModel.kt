package com.halcyonmobile.rsrvd.reservation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import com.halcyonmobile.rsrvd.core.reservation.ReservationRepository
import java.text.SimpleDateFormat
import java.util.*

class ReservationObjectFragmentViewModel : ViewModel() {
    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private val currentDate = Date()
    private val reservations: MutableLiveData<List<ReservationDto>> = MutableLiveData(emptyList())

    val upcomingReservations: LiveData<List<ReservationDto>> = Transformations.map(reservations) { list ->
        list.filter {
            try {
                sdf.parse(it.date)?.after(currentDate) ?: false
            } catch (e: Throwable) {
                Log.d("RESERVATION FVM", e.message ?: "No message")
                false
            }
        }
    }

    val historyReservations: LiveData<List<ReservationDto>> = Transformations.map(reservations) { list ->
        list.filter {
            try {
                sdf.parse(it.date)?.before(currentDate) ?: false
            } catch (e: Throwable) {
                Log.d("RESERVATION FVM", e.message ?: "No message")
                false
            }
        }
    }

    fun loadReservations() =
//        ReservationRepository.getReservations {
//            it?.let {
//                reservations.value = it
//            }
//        }
//      USE THESE DEFAULT VALUES FOR TESTING AS THERE ARE NO RESERVATIONS ON THE BACKEND RIGHT NOW
        ReservationRepository.loadDefaultReservations {
            reservations.value = it ?: emptyList()
        }

}