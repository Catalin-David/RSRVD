package com.halcyonmobile.rsrvd.core.venue

import com.halcyonmobile.rsrvd.core.shared.RetrofitManager
import com.halcyonmobile.rsrvd.core.venue.dto.Venue

class VenuesRemoteSource {
    private val venuesApi = RetrofitManager.retrofit!!.create(VenuesApi::class.java)

    fun getRecentlyVisitedVenues(callback: (List<Venue>?, Boolean) -> Unit) {
        venuesApi.getRecentlyVisitedVenues().enqueue(GetVenuesHandler(callback))
    }

    fun getExploreVenues(callback: (List<Venue>?, Boolean) -> Unit) {
        venuesApi.getExploreVenues().enqueue(GetVenuesHandler(callback))
    }
}