package com.halcyonmobile.rsrvd.core.venues

import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import retrofit2.Call
import retrofit2.http.GET

internal interface VenuesApi {
    @GET("venues/recently-visited")
    fun getRecentlyVisitedVenues(): Call<List<Venue>>

    @GET("venues/explore")
    fun getExploreVenues(): Call<List<Venue>>
}