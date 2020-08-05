package com.halcyonmobile.rsrvd.core.shared

import com.halcyonmobile.rsrvd.core.shared.repository.LocalUserRepository
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request().newBuilder()
            .addHeader("Authorization", "${LocalUserRepository.accessToken}")
            .build()
    )
}
