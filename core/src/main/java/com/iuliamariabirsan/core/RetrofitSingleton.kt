package com.iuliamariabirsan.core

import com.iuliamariabirsan.core.api.HttpClientFactoryAuth
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitSingleton {

    fun get(): Retrofit = Retrofit.Builder()
        .baseUrl("https://app.swaggerhub.com/")
        .client(HttpClientFactoryAuth.getClient())
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
}