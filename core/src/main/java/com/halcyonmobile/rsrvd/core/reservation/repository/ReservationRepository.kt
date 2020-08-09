package com.halcyonmobile.rsrvd.core.reservation.repository

import com.halcyonmobile.rsrvd.core.reservation.ReservationRemoteSource
import com.halcyonmobile.rsrvd.core.reservation.dto.ActivityDto
import com.halcyonmobile.rsrvd.core.reservation.dto.PriceDto
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import com.halcyonmobile.rsrvd.core.reservation.model.ReservationState
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.core.shared.Location
import com.halcyonmobile.rsrvd.core.venues.dto.DailyOpenHours
import com.halcyonmobile.rsrvd.core.venues.dto.Open
import com.halcyonmobile.rsrvd.core.venues.dto.Venue

object ReservationRepository {
    private val remoteSource = ReservationRemoteSource()

    fun getReservations(updateState: (List<ReservationDto>?) -> Unit){
        remoteSource.get(updateState)
    }

    fun loadDefaultReservations(updateState: (List<ReservationDto>?) -> Unit){
        val venue = Venue("1",
            "Baza sportiva Gheorgheni",
            "",
            "https://www.clujlife.com/wp-content/uploads/2016/12/baza-sportiva-gheorghieni-cluj.jpg",
            Location(),
            Open(DailyOpenHours(1f, 2f),DailyOpenHours(1f, 2f),DailyOpenHours(1f, 2f),DailyOpenHours(1f, 2f),DailyOpenHours(1f, 2f),DailyOpenHours(1f, 2f),DailyOpenHours(1f, 2f)),
            listOf())

        val list =  listOf(
            ReservationDto(
                id = "1",
                venue = venue,
                activity = ActivityDto(
                    "1",
                    venue,
                    "Running",
                    10,
                    PriceDto(
                        10,
                        2,
                        "hour"
                    )
                ),
                date = "2020-08-12T16:00:00.119Z",
                endDate = "2020-08-12T18:00:00.119Z",
                state = ReservationState.CONFIRMED,
                creator = "David Catalin"
            ),
            ReservationDto(
                id = "2",
                venue = venue,
                activity = ActivityDto(
                    "2",
                    venue,
                    "Basketball",
                    10,
                    PriceDto(
                        10,
                        2,
                        "hour"
                    )
                ),
                date = "2020-08-19T20:00:00.119Z",
                endDate = "2020-08-19T22:00:00.119Z",
                state = ReservationState.CONFIRMED,
                creator = "David Catalin"
            ),
            ReservationDto(
                id = "3",
                venue = venue,
                activity = ActivityDto(
                    "1",
                    venue,
                    "Football",
                    10,
                    PriceDto(
                        10,
                        3,
                        "hour"
                    )
                ),
                date = "2020-08-05T11:00:00.119Z",
                endDate = "2020-08-05T14:00:00.119Z",
                state = ReservationState.COMPLETED,
                creator = "David Catalin"
            ), ReservationDto(
                id = "4",
                venue = venue,
                activity = ActivityDto(
                    "1",
                    venue,
                    "Workout",
                    10,
                    PriceDto(
                        10,
                        2,
                        "hour"
                    )
                ),
                date = "2020-08-02T13:00:00.119Z",
                endDate = "2020-08-02T15:00:00.119Z",
                state = ReservationState.CANCELLED,
                creator = "David Catalin"
            )
        )
        updateState(list)
    }
}