package com.halcyonmobile.rsrvd.core.shared

import com.halcyonmobile.rsrvd.core.shared.interceptors.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal object RetrofitManager {
    val retrofitWithAuthorication: Retrofit = Retrofit.Builder()
        .baseUrl("https://39cutl7qwd.execute-api.eu-central-1.amazonaws.com/development/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://39cutl7qwd.execute-api.eu-central-1.amazonaws.com/development/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
}