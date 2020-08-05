package com.halcyonmobile.rsrvd.core.venues

import com.halcyonmobile.rsrvd.core.shared.RetrofitManager
import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import com.halcyonmobile.rsrvd.core.venues.dto.VenueById
import com.halcyonmobile.rsrvd.core.venues.handler.GetVenueByIdHandler
import com.halcyonmobile.rsrvd.core.venues.handler.GetVenuesHandler

class VenuesRemoteSource {
    private val venuesApi = RetrofitManager.retrofit!!.create(VenuesApi::class.java)

    fun getRecentlyVisitedVenues(callback: (List<Venue>?, Boolean) -> Unit) {
        venuesApi.getRecentlyVisitedVenues().enqueue(
            GetVenuesHandler(
                callback
            )
        )
    }

    fun getExploreVenues(callback: (List<Venue>?, Boolean) -> Unit) {
        venuesApi.getExploreVenues().enqueue(
            GetVenuesHandler(
                callback
            )
        )
    }

    fun getVenueById(venueId: String, callback: (VenueById) -> Unit) {
        venuesApi.getVenueById(venueId).enqueue(GetVenueByIdHandler(callback))
    }
}