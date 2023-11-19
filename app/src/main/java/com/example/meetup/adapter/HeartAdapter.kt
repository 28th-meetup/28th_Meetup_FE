package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.model.GetHeartListResponseModel
import com.example.meetup.model.GetHeartListResponseModelStore
import com.example.meetup.model.chatting.ChatListResult

class HeartAdapter(var heartList: ArrayList<GetHeartListResponseModelStore>) :
    RecyclerView.Adapter<HeartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeartAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_heart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeartAdapter.ViewHolder, position: Int) {
        val item = heartList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return heartList.size
    }


    interface ItemClick {
        fun onClick(view: View, position: Int)

    }

    var itemClick: ItemClick? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageview_heart_list: ImageView = view.findViewById(R.id.imageview_heart_list)
        var textview_store_name: TextView = view.findViewById(R.id.textview_store_name)
        var textview_rate: TextView = view.findViewById(R.id.textview_rate)
        var textview_min_order_price: TextView = view.findViewById(R.id.textview_min_order_price)

        fun bind(item: GetHeartListResponseModelStore) {


            Glide.with(itemView.context)
                .load(item.images[0])
                .into(imageview_heart_list)

            textview_store_name.text = item.name
            textview_min_order_price.text = item.minOrderAmount.toString()
            textview_rate.text =item.avgRate.toString()

            itemView.setOnClickListener {

                itemClick?.onClick(itemView, adapterPosition)

            }

        }
    }
}
