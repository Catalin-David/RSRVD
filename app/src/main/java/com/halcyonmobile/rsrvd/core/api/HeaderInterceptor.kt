package com.halcyonmobile.rsrvd.core.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .build())
}
