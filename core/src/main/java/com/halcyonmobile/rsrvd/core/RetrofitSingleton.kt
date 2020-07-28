package com.halcyonmobile.rsrvd.core

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
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
        OkHttpClient.Builder().apply {
            interceptors()
                .add(Interceptor { chain ->
                    val newRequest: Request =
                        chain.request()
                            .newBuilder()
                            .header("Content-Type", "application/json")
                            .build()
                    chain.proceed(newRequest)
                })
        }.build()
}

