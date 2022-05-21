package com.mambobryan.samba.data.remote

import com.mambobryan.samba.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apikey", BuildConfig.API_KEY)
            .build()

        val requestBuilder = original.newBuilder().url(url)

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}