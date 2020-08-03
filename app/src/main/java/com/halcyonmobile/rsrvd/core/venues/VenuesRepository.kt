package com.halcyonmobile.rsrvd.core.venues

import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import com.halcyonmobile.rsrvd.explorevenues.Card

object VenuesRepository {
    private val venuesRemoteSource = VenuesRemoteSource()

    fun getRecentlyVisitedVenues(callback: (List<Venue>?, Boolean) -> Unit) =
        venuesRemoteSource.getRecentlyVisitedVenues(callback)

    fun getExploreVenues(callback: (List<Venue>?, Boolean) -> Unit) =
        venuesRemoteSource.getExploreVenues(callback)

    fun search(term: String, callback: (List<Venue>?, Boolean) -> Unit) =
        venuesRemoteSource.search(term, callback)
}