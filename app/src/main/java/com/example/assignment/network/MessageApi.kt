package com.example.assignment.network

import com.example.assignment.network.data.MessageRequest
import com.example.assignment.network.data.MessagesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MessageApi {
    @GET("api/messages")
    suspend fun getAllMessages(): Response<List<MessagesResponse>>

    @POST("api/messages")
    suspend fun createMessage(@Body MessageRequest: MessageRequest): Response<MessagesResponse>

    @POST("api/reset")
    suspend fun resetMessages(): Response<Unit>
}