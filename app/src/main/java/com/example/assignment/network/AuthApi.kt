package com.example.assignment.network

import com.example.assignment.network.data.LoginRequest
import com.example.assignment.network.data.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/login")
    suspend fun login(@Body requestBody: LoginRequest): Response<LoginResponse>
}