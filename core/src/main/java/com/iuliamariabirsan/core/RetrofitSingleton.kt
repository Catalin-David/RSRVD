package com.iuliamariabirsan.core

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitSingleton {

    private val RetrofitSingle = Retrofit.Builder()
        .baseUrl("https://app.swaggerhub.com/")
        .client(getClient())
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()

    fun get(): Retrofit = RetrofitSingle

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