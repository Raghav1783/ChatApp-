package com.example.assignment.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.network.data.MessageRequest
import com.example.assignment.network.data.MessageResponse
import com.example.assignment.repository.MessageRepository
import com.example.assignment.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MessageViewModel@Inject constructor(private val MessageRepository: MessageRepository):
    ViewModel(){

    val allMessageLivedata
        get() = MessageRepository.allMessageResponseLiveData

    val chats: LiveData<NetworkResult<List<MessageResponse>>>
        get() = MessageRepository.ChatsResponseLiveData

    val status get() = MessageRepository.statusLiveData

    fun getMessages(){
        viewModelScope.launch {
            MessageRepository.getMessages()
        }
    }

    fun getChats(thread_id:String){
        viewModelScope.launch {
            MessageRepository.getChats(thread_id)

        }
    }

    fun createChat(messageRequest: MessageRequest){
        viewModelScope.launch {
            MessageRepository.createchats(messageRequest)

        }
    }

    fun DeleteChat(){
        viewModelScope.launch {
            MessageRepository.deleteChats()

        }
    }
}