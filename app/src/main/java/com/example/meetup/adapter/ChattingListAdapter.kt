package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.model.chatting.ChatListResult
import com.example.meetup.model.chatting.ChattingListResponseModel

class ChattingListAdapter(
     var chattingList: ArrayList<ChatListResult>
) :
    RecyclerView.Adapter<ChattingListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChattingListAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_chatting_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChattingListAdapter.ViewHolder, position: Int) {
        val item = chattingList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return chattingList.size
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)

    }

    var itemClick: ItemClick? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageview_chatting_image: ImageView = view.findViewById(R.id.imageview_chatting_image)
        var textview_chatting_name: TextView = view.findViewById(R.id.textview_chatting_name)
        var textview_chatting_content: TextView = view.findViewById(R.id.textview_chatting_content)
        var textview_chatting_time: TextView = view.findViewById(R.id.textview_chatting_time)
        var textview_chatting_num: TextView = view.findViewById(R.id.textview_chatting_num)

        fun bind(item: ChatListResult) {

            textview_chatting_name.text = item.sender
            textview_chatting_content.text = item.message
//            textview_chatting_time.text = item.
//            textview_chatting_num.text = item.

            itemView.setOnClickListener {

                itemClick?.onClick(itemView, adapterPosition)

            }

        }
    }

}