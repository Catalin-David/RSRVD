package com.halcyonmobile.rsrvd.core.shared

import com.halcyonmobile.rsrvd.core.shared.repository.UserLocalRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("Authorization", UserLocalRepository.accessToken!!)
                .build()
        )
}