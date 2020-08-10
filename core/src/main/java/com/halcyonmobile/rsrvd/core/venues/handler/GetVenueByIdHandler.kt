package com.halcyonmobile.rsrvd.core.venues.handler

import com.halcyonmobile.rsrvd.core.venues.dto.VenueById
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetVenueByIdHandler(private val callback: (VenueById) -> Unit) : Callback<VenueById> {
    override fun onFailure(call: Call<VenueById>, t: Throwable) {
    }

    override fun onResponse(call: Call<VenueById>, response: Response<VenueById>) {
        response.body()?.let { callback(it) }
    }
}