package com.iuliamariabirsan.core.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

class HttpClientFactoryAuth {
    companion object {
        fun getClient(): OkHttpClient =
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
}