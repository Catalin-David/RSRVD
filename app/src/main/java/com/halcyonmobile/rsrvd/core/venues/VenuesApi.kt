package com.halcyonmobile.rsrvd.core.venues

import com.halcyonmobile.rsrvd.core.venues.dto.SearchVenueBodyDto
import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VenuesApi {
    @GET("venues/recently-visited")
    fun getRecentlyVisitedVenues(): Call<List<Venue>>

    @GET("venues/explore")
    fun getExploreVenues(): Call<List<Venue>>

    @POST("venues/search")
    fun search(@Body body: SearchVenueBodyDto): Call<List<Venue>>
}