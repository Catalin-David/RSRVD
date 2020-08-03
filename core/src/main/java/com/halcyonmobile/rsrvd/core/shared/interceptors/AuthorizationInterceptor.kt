package com.halcyonmobile.rsrvd.core.shared.interceptors

import com.halcyonmobile.rsrvd.core.shared.State
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthorizationInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request().newBuilder()
            .addHeader("Authorization", "${State.authorization}")
            .build()
    )
}
