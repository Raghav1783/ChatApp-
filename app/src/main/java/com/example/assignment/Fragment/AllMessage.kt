package com.example.assignment.Fragment

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.Adapter.MessageAdapter
import com.example.assignment.R
import com.example.assignment.ViewModels.MessageViewModel
import com.example.assignment.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMessage : Fragment() {
    private lateinit var messageAdapter: MessageAdapter
    private val MessageViewModel by viewModels<MessageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_message, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.chatListRecyclerView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.toolbar_menu)

        messageAdapter = MessageAdapter(::onThreadClicked)
        recyclerView.adapter = messageAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        MessageViewModel.allMessageLivedata.observe(viewLifecycleOwner) { result ->
            progressBar.isVisible = result is NetworkResult.Loading
            when (result) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "onCreateView: yo")
                    messageAdapter.submitList(result.data){
                        recyclerView.scrollToPosition( 0)
                    }
                }
                is NetworkResult.Error -> {
                    Log.e(TAG, "Error: ${result.message}")
                }
                is NetworkResult.Loading -> {

                }
            }
        }

        MessageViewModel.getMessages()
        return view
    }




    private fun onThreadClicked(message:String){
        val bundle = Bundle()
        bundle.putString("thread_id",message)
        findNavController().navigate(R.id.action_allMessage_to_chatScreen,bundle)

    }
}