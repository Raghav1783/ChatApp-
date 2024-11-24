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
    private var threadid: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_screen, container, false)
        threadid = arguments?.getString("thread_id")

        val heading = view.findViewById<TextView>(R.id.chatHeading)
        val recyclerView = view.findViewById<RecyclerView>(R.id.chatRecyclerView)
        val messageET = view.findViewById<EditText>(R.id.messageEditText)
        val sendbtn = view.findViewById<Button>(R.id.sendButton)

        heading.text = "Thread number $threadid"
        chatAdapter = ChatAdapter()
        recyclerView.adapter = chatAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        sendbtn.setOnClickListener {
            val messageText = messageET.text?.toString()
            if (!messageText.isNullOrBlank()) {
                MessageViewModel.createChat(MessageRequest(threadid.orEmpty(), messageText))
                messageET.text.clear()

            }
        }
        MessageViewModel.getChats(threadid.toString())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val recyclerView = view.findViewById<RecyclerView>(R.id.chatRecyclerView)

        MessageViewModel.chats.observe(viewLifecycleOwner) { result ->
            progressBar.isVisible = result is NetworkResult.Loading
            when (result) {
                is NetworkResult.Success -> {
                    chatAdapter.submitList(result.data) {
                        recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
                    }
                }
                is NetworkResult.Error -> {
                    Log.e("ChatFragment", "Error: ${result.message}")
                }
                is NetworkResult.Loading -> {

                }
            }
        }

        MessageViewModel.status.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    threadid?.let {
                        MessageViewModel.getChats(it)

                    }
                }
                is NetworkResult.Error -> {
                    Log.e("ChatFragment", "Status Error: ${result.message}")
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }
}