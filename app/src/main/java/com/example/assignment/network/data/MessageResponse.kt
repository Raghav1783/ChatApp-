package com.example.assignment.network.data

data class MessagesResponse(
    val id: Int,
    val thread_id: Int,
    val user_id: String,
    val body: String,
    val timestamp: String,
    val agent_id: Int?
)
