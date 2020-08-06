package com.halcyonmobile.rsrvd.core.venues

import com.halcyonmobile.rsrvd.core.venues.dto.Venue
import retrofit2.Call
import retrofit2.Callback

internal class GetVenuesHandler(private val callback: (List<Venue>?, Boolean) -> Unit) : Callback<List<Venue>> {
    override fun onResponse(call: Call<List<Venue>>, response: retrofit2.Response<List<Venue>>) {
        callback(response.body(), response.code() != 200)
    }

    override fun onFailure(call: Call<List<Venue>>, t: Throwable) {
        callback(null, true)
    }
}
