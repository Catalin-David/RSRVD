package com.halcyonmobile.rsrvd.core.reservation

import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto

object ReservationRepository {
    private val remoteSource = ReservationRemoteSource()

    fun getReservations(updateState: (List<ReservationDto>?) -> Unit) = remoteSource.get(updateState)

    fun createReservation(
        id: String,
        start: String,
        end: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit) {
        remoteSource.createReservation(id, start, end, onSuccess, onFailure)
    }

    fun getReservationWithId(id: String, updateState: (ReservationDto?) -> Unit) = remoteSource.getWithId(id, updateState)
    fun cancelReservation(id: String) = remoteSource.cancelReservationWithId(id)
}

