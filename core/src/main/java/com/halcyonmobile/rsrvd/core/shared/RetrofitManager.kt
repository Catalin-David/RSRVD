package com.halcyonmobile.rsrvd.core.shared

import com.halcyonmobile.rsrvd.core.shared.interceptors.AuthorizationInterceptor
import com.halcyonmobile.rsrvd.core.shared.interceptors.ContentTypeInterceptor
import com.halcyonmobile.rsrvd.core.shared.interceptors.Interceptors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitManager {
    fun getWithInterceptors(interceptorsToApply: List<Interceptors>): Retrofit = Retrofit.Builder()
        .baseUrl("https://39cutl7qwd.execute-api.eu-central-1.amazonaws.com/development/")
        .client(
            OkHttpClient.Builder()
                .apply {
                    if (interceptorsToApply.contains(Interceptors.Authorization)) {
                        addInterceptor(AuthorizationInterceptor())
                    }
                    if (interceptorsToApply.contains(Interceptors.ContentType)) {
                        addInterceptor(ContentTypeInterceptor())
                    }
                }
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()
}