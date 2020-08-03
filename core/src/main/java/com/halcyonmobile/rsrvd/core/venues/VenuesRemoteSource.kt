package com.halcyonmobile.rsrvd.core.venues

import com.halcyonmobile.rsrvd.core.shared.RetrofitManager
import com.halcyonmobile.rsrvd.core.shared.interceptors.Interceptors
import com.halcyonmobile.rsrvd.core.venues.models.Venue

class VenuesRemoteSource {
    private val venuesApi = RetrofitManager.getWithInterceptors(listOf(Interceptors.Authorization)).create(VenuesApi::class.java)

    fun getRecentlyVisitedVenues(callback: (List<Venue>?, Boolean) -> Unit) {
        venuesApi.getRecentlyVisitedVenues().enqueue(GetVenuesHandler(callback))
    }

    fun getExploreVenues(callback: (List<Venue>?, Boolean) -> Unit) {
        venuesApi.getExploreVenues().enqueue(GetVenuesHandler(callback))
    }
}