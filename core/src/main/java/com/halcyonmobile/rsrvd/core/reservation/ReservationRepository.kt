package com.halcyonmobile.rsrvd.core.reservation

class ReservationRepository {
    private val reservationRemoteSource = ReservationRemoteSource()

    fun createReservation(
        id: String,
        star: String,
        end: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit) {
        reservationRemoteSource.createReservation(id, star, end, onSuccess, onFailure)
    }
}
