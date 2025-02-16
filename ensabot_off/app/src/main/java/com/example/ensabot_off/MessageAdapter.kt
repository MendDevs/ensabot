package com.example.ensabot_off

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private var messageList: List<Message>) : RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                // Inflate the chat item layout with the parent and attach it to the parent view
                val chatView = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
                return MyViewHolder(chatView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                val message = messageList[position]
                if (message.sentBy == Message.SENT_BY_ME) {
                        holder.leftChatView.visibility = View.GONE
                        holder.rightChatView.visibility = View.VISIBLE
                        holder.rightTextView.text = message.text
                } else {
                        holder.rightChatView.visibility = View.GONE
                        holder.leftChatView.visibility = View.VISIBLE
                        holder.leftTextView.text = message.text
                }
        }

        override fun getItemCount(): Int = messageList.size

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val leftChatView: LinearLayout = itemView.findViewById(R.id.left_chat_view)
                val rightChatView: LinearLayout = itemView.findViewById(R.id.right_chat_view)
                val leftTextView: TextView = itemView.findViewById(R.id.left_chat_text_view)
                val rightTextView: TextView = itemView.findViewById(R.id.right_chat_text_view)
        }
}
