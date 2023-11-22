package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.model.chatting.ChattingDataModel

class ChattingAdapter(var chattingData: ArrayList<ChattingDataModel>) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    companion object {
//        const val TYPE_ONE = 1
//        const val TYPE_TWO = 2
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingAdapter.ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.recyclerview_item_chatting_type_1, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ChattingAdapter.ViewHolder, position: Int) {
//        val item = chattingData[position]
//        holder.bind(item)
//    }
//
//    override fun getItemCount(): Int {
//        return chattingData.size
//    }
//
//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//
//        var textview_chatting: TextView = view.findViewById(R.id.textview_chatting_live)
//
//
//        fun bind(item: ChattingDataModel) {
//
////            textview_chatting_name.text = item.sender
//
//            textview_chatting.text = item.message
//
////            itemView.setOnClickListener {
////
////                itemClick?.onClick(itemView, adapterPosition)
////
////            }
//
//        }
//    }
companion object {
    const val TYPE_ONE = 1
    const val TYPE_TWO = 2
}

    override fun getItemViewType(position: Int): Int {
        return if (chattingData[position].isTypeOne) {
            TYPE_ONE
        } else {
            TYPE_TWO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ONE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_chatting_type_1, parent, false)
                ViewHolderTypeOne(view)
            }
            TYPE_TWO -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_chatting_type_2, parent, false)
                ViewHolderTypeTwo(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_ONE -> {
                val viewHolderTypeOne = holder as ViewHolderTypeOne
                viewHolderTypeOne.bind(chattingData[position])
            }
            TYPE_TWO -> {
                val viewHolderTypeTwo = holder as ViewHolderTypeTwo
                viewHolderTypeTwo.bind(chattingData[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return chattingData.size
    }

    // ViewHolderTypeOne 및 ViewHolderTypeTwo 클래스 정의
    inner class ViewHolderTypeOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTypeOne: TextView = itemView.findViewById(R.id.textview_chatting_live)

        fun bind(data: ChattingDataModel) {
            textViewTypeOne.text = data.message
        }
    }

    inner class ViewHolderTypeTwo(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTypeTwo: TextView = itemView.findViewById(R.id.textview_chatting_live_2)

        fun bind(data: ChattingDataModel) {
            textViewTypeTwo.text = data.message
        }
    }
}