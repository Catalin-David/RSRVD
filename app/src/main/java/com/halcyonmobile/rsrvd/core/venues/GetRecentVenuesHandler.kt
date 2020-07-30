package com.halcyonmobile.rsrvd.core.venues

import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import retrofit2.Call
import retrofit2.Callback

class GetRecentVenuesHandler(private val state: (Boolean) -> Unit) : Callback<Venue> {
    override fun onResponse(call: Call<Venue>, response: retrofit2.Response<Venue>) {
        state(response.code() == 200)
    }

    override fun onFailure(call: Call<Venue>, t: Throwable) {
        state(false)
    }
}