package com.example.assignment.network

import com.example.assignment.network.data.MessageRequest
import com.example.assignment.network.data.MessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MessageApi {
    @GET("api/messages")
    suspend fun getAllMessages(): Response<List<MessageResponse>>

    @POST("api/messages")
    suspend fun createMessage(@Body MessageRequest: MessageRequest): Response<MessageResponse>

    @POST("api/reset")
    suspend fun resetMessages(): Response<Unit>
}