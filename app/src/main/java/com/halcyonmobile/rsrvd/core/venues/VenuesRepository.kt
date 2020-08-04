package com.halcyonmobile.rsrvd.core.venues

import com.halcyonmobile.rsrvd.core.venues.dto.FilterDto
import com.halcyonmobile.rsrvd.core.venues.dto.Venue

object VenuesRepository {
    private val venuesRemoteSource = VenuesRemoteSource()

    fun getRecentlyVisitedVenues(callback: (List<Venue>?, Boolean) -> Unit) {
        venuesRemoteSource.getRecentlyVisitedVenues(callback)
    }

    fun getExploreVenues(callback: (List<Venue>?, Boolean) -> Unit) {
        venuesRemoteSource.getExploreVenues(callback)
    }

    fun filterVenues(dto: FilterDto, callback: (List<Venue>?, Boolean) -> Unit) {
        venuesRemoteSource.filterVenues(dto, callback)
    }
}
