package com.example.assignment.Fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.Adapter.ChatAdapter
import com.example.assignment.R
import com.example.assignment.ViewModels.MessageViewModel
import com.example.assignment.network.data.MessageRequest
import com.example.assignment.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChatScreen : Fragment() {
    private lateinit var chatAdapter: ChatAdapter
    private val MessageViewModel by viewModels<MessageViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_screen, container, false)
        val threadid = arguments?.getString("thread_id")

        val heading = view.findViewById<TextView>(R.id.chatHeading)
        val recyclerView = view.findViewById<RecyclerView>(R.id.chatRecyclerView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val messageET = view.findViewById<EditText>(R.id.messageEditText)
        val sendbtn = view.findViewById<Button>(R.id.sendButton)

        heading.text = "Thread number $threadid"
        chatAdapter = ChatAdapter()
        recyclerView.adapter = chatAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        MessageViewModel.chats.observe(viewLifecycleOwner) { result ->
            progressBar.isVisible = result is NetworkResult.Loading
            when (result) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "onCreateView: yo")
                    chatAdapter.submitList(result.data) {
                        recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
                    }
                }
                is NetworkResult.Error -> {
                    Log.e(TAG, "Error: ${result.message}")
                }
                is NetworkResult.Loading -> {

                }
            }
        }

        sendbtn.setOnClickListener{
            if(messageET.text!=null)
                MessageViewModel.createChat(MessageRequest(threadid.toString(),messageET.text.toString()))
            messageET.text.clear()
            MessageViewModel.getChats(threadid.toString())

        }

        MessageViewModel.getChats(threadid.toString())
        return view
    }

}