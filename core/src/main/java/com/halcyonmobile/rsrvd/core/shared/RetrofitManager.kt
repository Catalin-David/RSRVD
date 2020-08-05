package com.halcyonmobile.rsrvd.core.shared

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal object RetrofitManager {
    var retrofitWithAuthentication: Retrofit? = null
        get() {
            if (field == null) {
                field = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(
                        OkHttpClient.Builder()
                            .addInterceptor(AuthorizationInterceptor())
                            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build()
                    )
                    .addConverterFactory(MoshiConverterFactory.create().asLenient())
                    .build()
            }
            return field
        }

    var retrofit: Retrofit? = null
        get() {
            if (field == null) {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(
                        OkHttpClient.Builder()
                            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build()
                    )
                    .addConverterFactory(MoshiConverterFactory.create().asLenient())
                    .build()
            }
            return field
        }

    private const val BASE_URL = "https://39cutl7qwd.execute-api.eu-central-1.amazonaws.com/development/"
}