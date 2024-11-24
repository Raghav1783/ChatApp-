package com.example.assignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment.network.MessageApi
import com.example.assignment.network.data.MessageRequest
import com.example.assignment.network.data.MessageResponse
import com.example.assignment.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class MessageRepository @Inject constructor(private val messageApi: MessageApi) {
    private val _allMessageResponseLiveData = MutableLiveData<NetworkResult<List<MessageResponse>>>()
    val allMessageResponseLiveData: LiveData<NetworkResult<List<MessageResponse>>>
        get() = _allMessageResponseLiveData

    private val _ChatsResponseLiveData = MutableLiveData<NetworkResult<List<MessageResponse>>>()
    val ChatsResponseLiveData: LiveData<NetworkResult<List<MessageResponse>>>
        get() = _ChatsResponseLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<List<MessageResponse>>>()
    val statusLiveData: LiveData<NetworkResult<List<MessageResponse>>>
        get() = _statusLiveData

    suspend fun createchats(messageRequest: MessageRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        messageApi.createMessage(messageRequest)
    }

    suspend fun getMessages(){
        _allMessageResponseLiveData.postValue(NetworkResult.Loading())
        try {
            val response = messageApi.getAllMessages()
            if (response.isSuccessful && response.body() != null) {
                val messages = response.body()!!

                val distinctMessages = filterDistinctMessages(messages)
                _allMessageResponseLiveData.postValue(NetworkResult.Success(distinctMessages))
            } else {
                val erorobj = JSONObject(response.errorBody()!!.charStream().readText())
                _allMessageResponseLiveData.postValue(NetworkResult.Error(erorobj.getString("error")))
            }
        } catch (e: Exception) {
            _allMessageResponseLiveData.postValue(NetworkResult.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    suspend fun getChats(thread_id:String){
        _ChatsResponseLiveData.postValue(NetworkResult.Loading())
        try {
            val response = messageApi.getAllMessages()
            if (response.isSuccessful && response.body() != null) {
                val messages = response.body()!!

                val distinctMessages = filterDistinctChats(messages,thread_id)
                _ChatsResponseLiveData.postValue(NetworkResult.Success(distinctMessages))
            } else {
                val erorobj = JSONObject(response.errorBody()!!.charStream().readText())
                _ChatsResponseLiveData.postValue(NetworkResult.Error(erorobj.getString("error")))
            }
        } catch (e: Exception) {
            _ChatsResponseLiveData.postValue(NetworkResult.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    private fun filterDistinctChats(messages: List<MessageResponse>, threadId: String): List<MessageResponse> {
        return messages
            .filter {  it.thread_id.toString() == threadId }
            .sortedBy { it.timestamp }
    }

    private fun filterDistinctMessages(messages: List<MessageResponse>): List<MessageResponse> {
        return messages
            .groupBy { it.thread_id }
            .map { (_, group) -> group.maxByOrNull { it.id }!! }.sortedByDescending { it.timestamp }
    }

}