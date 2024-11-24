package com.example.assignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment.network.AuthApi
import com.example.assignment.network.data.LoginRequest
import com.example.assignment.network.data.LoginResponse
import com.example.assignment.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class LoginRepository@Inject constructor(private val authApi: AuthApi) {
    private val _loginResponseLiveData = MutableLiveData<NetworkResult<LoginResponse>>()
    val loginResponseLiveData: LiveData<NetworkResult<LoginResponse>>
        get() = _loginResponseLiveData


    suspend fun loginUser(userRequest: LoginRequest){
        _loginResponseLiveData.postValue(NetworkResult.Loading())
        try {
            val response = authApi.login(userRequest)
            if (response.isSuccessful && response.body() != null) {
                _loginResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
            } else {
                val erorobj = JSONObject(response.errorBody()!!.charStream().readText())
                _loginResponseLiveData.postValue(NetworkResult.Error(erorobj.getString("error")))
            }
        } catch (e: Exception) {
            _loginResponseLiveData.postValue(NetworkResult.Error(e.message ?: "An unexpected error occurred"))
        }
    }

}