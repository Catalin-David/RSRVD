package com.halcyonmobile.rsrvd.core.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitManager {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://app.swaggerhub.com/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(HttpLoggingInterceptor())
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
}