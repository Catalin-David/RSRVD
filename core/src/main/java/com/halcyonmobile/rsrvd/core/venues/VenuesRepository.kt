package com.halcyonmobile.rsrvd.core.venues

import com.halcyonmobile.rsrvd.core.venues.dto.Venue

class VenuesRepository {
    private val venuesRemoteSource = VenuesRemoteSource()

    fun getRecentlyVisitedVenues(callback: (List<Venue>?, Boolean) -> Unit) {
        venuesRemoteSource.getRecentlyVisitedVenues(callback)
    }

    fun getExploreVenues(callback: (List<Venue>?, Boolean) -> Unit) {
        venuesRemoteSource.getExploreVenues(callback)
    }
}