package com.example.assignment.network

import com.example.assignment.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderIntercepetor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getToken()
        val request = chain.request().newBuilder()
            .addHeader("X-Branch-Auth-Token", token.toString())
            .build()
        return chain.proceed(request)
    }

}