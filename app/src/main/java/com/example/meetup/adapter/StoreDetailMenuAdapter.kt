package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.model.store.GetStoreMenuResponseModel
import com.example.meetup.model.store.GetStoreMenuResponseModelResult
import com.example.meetup.model.store.StoreDetailMenuResponseModel

class StoreDetailMenuAdapter(private var storeDetailMenuList : ArrayList<GetStoreMenuResponseModelResult>) :
    RecyclerView.Adapter<StoreDetailMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreDetailMenuAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_store_detail_menu,parent,false)
        return ViewHolder(view)
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)

    }

    var itemClick : ItemClick? = null

    override fun getItemCount(): Int {
    return storeDetailMenuList.size
    }

    override fun onBindViewHolder(holder: StoreDetailMenuAdapter.ViewHolder, position: Int) {
        val item = storeDetailMenuList[position]
        holder.bind(item)
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageview_menu : ImageView = view.findViewById(R.id.imageview_menu)
        var textview_menu_name : TextView = view.findViewById(R.id.textview_menu_name)
        var textview_menu_price : TextView = view.findViewById(R.id.textview_menu_price)

        fun bind(item: GetStoreMenuResponseModelResult) {

            Glide.with(itemView.context)
                .load(item.image)
                .into(imageview_menu)


            textview_menu_name.text = item.name
            textview_menu_price.text = item.dollarPrice.toString()

            itemView.setOnClickListener {

                itemClick?.onClick(itemView,adapterPosition)

            }

        }
    }
}