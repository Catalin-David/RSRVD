package com.halcyonmobile.rsrvd.core.shared.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ContentTypeInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
    )
}
