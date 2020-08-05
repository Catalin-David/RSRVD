package com.halcyonmobile.rsrvd.core

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request().newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyX2NrZDVyMXpsZzAwMDAwN21nNWY3OTVlZzAiLCJpYXQiOjE1OTYwMjU5NjAsImV4cCI6MTU5NjExMjM2MCwiYXVkIjoiYnJhbWJsZTphY2Nlc3MifQ.Q8kvIWJnb6-SoYO4WOukU1v-r34XJ8k0DtK7Ia6naHYHyeKnMkKU_6ePFA5HJbBh9Bf1wNW1RIR8BcKd1BbaQlYTMYg8x9Y-ARxjQ6K9MEuFGcYodBftgRp7LadiRVyyhKXgocUn4Q0axb2L23WyA8DwSC9b3bYbuerA-xdL6vs")
            .build()
    )
}
