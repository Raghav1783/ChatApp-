package com.example.assignment.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.network.data.MessageResponse
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class MessageAdapter(private val onThreadClicked: (String) -> Unit) : ListAdapter<MessageResponse, MessageAdapter.MessageViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.allmessage_item, parent, false)
        return MessageViewHolder(view,onThreadClicked)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    class MessageViewHolder(itemView: View, private val onThreadClicked: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val senderTextView: TextView = itemView.findViewById(R.id.senderName)
        private val messageTextView: TextView = itemView.findViewById(R.id.lastMessage)
        private val dateTextView: TextView = itemView.findViewById(R.id.timestamp)

        fun bind(message: MessageResponse) {
            senderTextView.text = message.user_id
            messageTextView.text = message.body
            dateTextView.text = formatTimestamp(message.timestamp)
            itemView.setOnClickListener{
                onThreadClicked(message.thread_id.toString())
            }
        }

        fun formatTimestamp(input: String): String {

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormat = SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.getDefault())

            val date = inputFormat.parse(input)
            return outputFormat.format(date!!)
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<MessageResponse>() {
        override fun areItemsTheSame(oldItem: MessageResponse, newItem: MessageResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageResponse, newItem: MessageResponse): Boolean {
            return oldItem == newItem
        }
    }



}
