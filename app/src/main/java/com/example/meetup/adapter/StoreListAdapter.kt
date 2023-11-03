package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.model.StoreListResponseModel

class StoreListAdapter(private var storeList : ArrayList<StoreListResponseModel>) :
RecyclerView.Adapter<StoreListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreListAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_store,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: StoreListAdapter.ViewHolder, position: Int) {
            val item = storeList[position]
    holder.bind(item)}

    override fun getItemCount(): Int {

        return  storeList.size   }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageview_1 : ImageView = view.findViewById(R.id.imageview_1)
        var imageview_2 : ImageView = view.findViewById(R.id.imageview_2)
        var imageview_3 : ImageView = view.findViewById(R.id.imageview_3)
        var textview_store_name :TextView = view.findViewById(R.id.textview_store_name)
        var textview_least_price : TextView = view.findViewById(R.id.textview_least_price)
        var textview_rate : TextView = view.findViewById(R.id.textview_rate)

        fun bind(item: StoreListResponseModel) {


//            Glide.with(itemView.context)
//                .load(item.imageview_1)
//                .into(imageview_1)
//            Glide.with(itemView.context)
//                .load(item.imageview_2)
//                .into(imageview_2)
//            Glide.with(itemView.context)
//                .load(item.imageview_3)
//                .into(imageview_3)
            textview_store_name.text = item.textview_store_name
            textview_least_price.text = item.textview_least_price
            textview_rate.text = item.textview_rate

        }
    }
}