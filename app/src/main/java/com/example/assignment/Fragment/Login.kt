package com.example.assignment.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.ViewModels.AuthViewModel
import com.example.assignment.network.data.LoginRequest
import com.example.assignment.utils.NetworkResult
import com.example.assignment.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Login : Fragment() {
    private val authViewModel by viewModels<AuthViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_login, container, false)

        if(tokenManager.getToken()!=null){
            findNavController().navigate(R.id.action_login_to_allMessage)
        }

        val loginButton: Button = view.findViewById(R.id.loginButton)

        loginButton.setOnClickListener {

            val emailEditText: EditText = view.findViewById(R.id.usernameEditText)
            val passwordEditText: EditText = view.findViewById(R.id.passwordEditText)

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            authViewModel.loginUser(LoginRequest(email,password))

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.userResponseLivedata.observe(viewLifecycleOwner, Observer {
            view.findViewById<ProgressBar>(R.id.progressBar).isVisible = false
            when(it){
                is NetworkResult.Success ->{
                    tokenManager.saveToken(it.data!!.auth_token)
                    view.findViewById<TextView>(R.id.errorTextView).text = tokenManager.getToken()
                    findNavController().navigate(R.id.action_login_to_allMessage)
                }
                is NetworkResult.Error ->{
                    view.findViewById<TextView>(R.id.errorTextView).text = it.message
                }
                is NetworkResult.Loading ->{
                    view.findViewById<ProgressBar>(R.id.progressBar).isVisible = true

                }
            }
        })
    }


}