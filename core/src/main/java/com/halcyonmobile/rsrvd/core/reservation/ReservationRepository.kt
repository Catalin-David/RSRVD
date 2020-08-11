package com.halcyonmobile.rsrvd.core.reservation

object ReservationRepository {
    private val reservationRemoteSource = ReservationRemoteSource()

    fun createReservation(
        id: String,
        start: String,
        end: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit) {
        reservationRemoteSource.createReservation(id, start, end, onSuccess, onFailure)
    }
}
