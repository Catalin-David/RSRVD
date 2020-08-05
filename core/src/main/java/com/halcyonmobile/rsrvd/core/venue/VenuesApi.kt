package com.halcyonmobile.rsrvd.core.venue

import com.halcyonmobile.rsrvd.core.venue.dto.Venue
import retrofit2.Call
import retrofit2.http.GET

interface VenuesApi {
    @GET("venues/recently-visited")
    fun getRecentlyVisitedVenues(): Call<List<Venue>>

    @GET("venues/explore")
    fun getExploreVenues(): Call<List<Venue>>
}