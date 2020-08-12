package com.halcyonmobile.rsrvd.core.reservation.repository

import com.halcyonmobile.rsrvd.core.reservation.ReservationRemoteSource
import com.halcyonmobile.rsrvd.core.reservation.dto.ActivityDto
import com.halcyonmobile.rsrvd.core.reservation.dto.PriceDto
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import com.halcyonmobile.rsrvd.core.reservation.model.ReservationState
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.venues.dto.StartEndHours
import com.halcyonmobile.rsrvd.core.venues.dto.Open
import com.halcyonmobile.rsrvd.core.venues.dto.Venue

object ReservationRepository {
    private val remoteSource = ReservationRemoteSource()

    fun getReservations(updateState: (List<ReservationDto>?) -> Unit) = remoteSource.get(updateState)
    fun getReservationWithId(id: String, updateState: (ReservationDto?) -> Unit) = remoteSource.getWithId(id, updateState)
    fun cancelReservation(id: String) = remoteSource.cancelReservationWithId(id)
}
