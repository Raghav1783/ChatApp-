package com.example.assignment.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.network.data.LoginRequest
import com.example.assignment.network.data.LoginResponse
import com.example.assignment.repository.LoginRepository
import com.example.assignment.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel(){

    val userResponseLivedata : LiveData<NetworkResult<LoginResponse>>
        get() = loginRepository.loginResponseLiveData

    fun loginUser(userRequest: LoginRequest){
        viewModelScope.launch {
            loginRepository.loginUser(userRequest)
        }

    }
}