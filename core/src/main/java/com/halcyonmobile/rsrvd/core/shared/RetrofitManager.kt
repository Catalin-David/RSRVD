package com.halcyonmobile.rsrvd.core.shared

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

internal object RetrofitManager {
    var retrofitWithAuthentication: Retrofit? = null
        get() {
            if (field == null) {
                field = Retrofit.Builder()
                    .baseUrl("https://39cutl7qwd.execute-api.eu-central-1.amazonaws.com/development/")
                    .client(
                        OkHttpClient.Builder()
                            .addInterceptor { chain ->
                                chain.proceed(
                                    chain.request().newBuilder()
                                        .addHeader("Authorization", State.authorization)
                                        .build()
                                )
                            }
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
                    .baseUrl("https://39cutl7qwd.execute-api.eu-central-1.amazonaws.com/development/")
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
}