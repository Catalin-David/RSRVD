package com.halcyonmobile.rsrvd.core.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AppService {
    @POST("/me")
    fun update(@Body body: UpdateUserBody): Call<String>
}
