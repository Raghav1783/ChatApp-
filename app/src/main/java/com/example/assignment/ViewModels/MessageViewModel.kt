package com.example.assignment.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MessageViewModel@Inject constructor(private val MessageRepository: MessageRepository):
    ViewModel(){

    val allMessageLivedata
        get() = MessageRepository.allMessageResponseLiveData


    fun getMessages(){
        viewModelScope.launch {
            MessageRepository.getMessages()
        }
    }
}