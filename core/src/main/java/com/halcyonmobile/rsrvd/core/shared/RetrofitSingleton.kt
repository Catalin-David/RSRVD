package com.halcyonmobile.rsrvd.core.shared

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitSingleton {

    private val retrofitSingle = Retrofit.Builder()
        .baseUrl("https://39cutl7qwd.execute-api.eu-central-1.amazonaws.com/development/")
        .client(getClient())
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()

    fun get(): Retrofit = retrofitSingle

    private fun getClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
}

