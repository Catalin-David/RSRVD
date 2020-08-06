package com.halcyonmobile.rsrvd.core.venues

import com.halcyonmobile.rsrvd.core.shared.RetrofitManager
import com.halcyonmobile.rsrvd.core.venues.dto.FilterDto
import com.halcyonmobile.rsrvd.core.venues.dto.SearchVenueBodyDto
import com.halcyonmobile.rsrvd.core.venues.dto.Venue

internal class VenuesRemoteSource {
    private val venuesApi = RetrofitManager.retrofitWithAuthentication!!.create(VenuesApi::class.java)

    fun getRecentlyVisitedVenues(callback: (List<Venue>?, Boolean) -> Unit) =
        venuesApi.getRecentlyVisitedVenues().enqueue(GetVenuesHandler(callback))

    fun getExploreVenues(callback: (List<Venue>?, Boolean) -> Unit) =
        venuesApi.getExploreVenues().enqueue(GetVenuesHandler(callback))

    fun search(term: String, callback: (List<Venue>?, Boolean) -> Unit) =
        venuesApi.search(SearchVenueBodyDto(name = term)).enqueue(GetVenuesHandler(callback))

    fun filterVenues(dto: FilterDto, callback: (List<Venue>?, Boolean) -> Unit) =
        venuesApi.filterVenues(dto).enqueue(GetVenuesHandler(callback))
}