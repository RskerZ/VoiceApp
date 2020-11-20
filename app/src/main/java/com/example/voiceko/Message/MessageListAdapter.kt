package com.example.voiceko.Message

import android.R.id
import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.voiceko.R


class MessageListAdapter(val context: Context, messageList: List<UserMessage>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED  = 2

    var mContext:Context = context
    var mMessageList:List<UserMessage> = messageList






    override fun getItemCount(): Int {
        return mMessageList.size
    }
    //要修改
    override fun getItemViewType(position: Int): Int {
        val message: UserMessage = mMessageList[position]
        return if(message.id == 1){
            VIEW_TYPE_MESSAGE_SENT
        }else{
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var message = mMessageList[position]

        when(holder.itemViewType){
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(message)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(message)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val view: View
        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_sent, parent, false)
            return SentMessageHolder(view)
        }else if(viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_received, parent, false)
            return ReceivedMessageHolder(view)
        }
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_received, parent, false)
        return ReceivedMessageHolder(view)
    }




    inner class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageText: TextView = itemView.findViewById(R.id.text_message_body)
        var timeText: TextView = itemView.findViewById(R.id.text_message_time)
        fun bind(message: UserMessage){
            messageText.text = message.message
            timeText.text = Utils.formatTime(message.createdAt)
        }
    }
    inner class ReceivedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageText: TextView = itemView.findViewById(R.id.text_message_body)
        var timeText: TextView = itemView.findViewById(R.id.text_message_time)
        var nameText: TextView = itemView.findViewById(R.id.text_message_name)
        fun bind(message: UserMessage){
            messageText.text = message.message
            timeText.text = Utils.formatTime(message.createdAt)
            nameText.text = message.nickname
        }
    }
}