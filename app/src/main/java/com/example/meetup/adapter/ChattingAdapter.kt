package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.model.chatting.ChattingDataModel

class ChattingAdapter(var chattingData: ArrayList<ChattingDataModel>) :  RecyclerView.Adapter<ChattingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_chatting, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChattingAdapter.ViewHolder, position: Int) {
        val item = chattingData[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return chattingData.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var textview_chatting: TextView = view.findViewById(R.id.textview_chatting_live)


        fun bind(item: ChattingDataModel) {

//            textview_chatting_name.text = item.sender

            textview_chatting.text = item.message

//            itemView.setOnClickListener {
//
//                itemClick?.onClick(itemView, adapterPosition)
//
//            }

        }
    }

}