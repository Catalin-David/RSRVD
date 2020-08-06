package com.halcyonmobile.rsrvd.core.venues

import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import com.halcyonmobile.rsrvd.core.venues.dto.VenueById
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal interface VenuesApi {
    @GET("venues/recently-visited")
    fun getRecentlyVisitedVenues(): Call<List<Venue>>

    @GET("venues/explore")
    fun getExploreVenues(): Call<List<Venue>>

    @GET("venues/{venueId}")
    fun getVenueById(@Path("venueId") venueId: String): Call<VenueById>
}