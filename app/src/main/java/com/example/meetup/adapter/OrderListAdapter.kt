package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.model.OrderListResponseModel
import com.google.android.material.card.MaterialCardView


class OrderListAdapter(
    private var orderList: ArrayList<OrderListResponseModel>,
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderListAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_order_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderListAdapter.ViewHolder, position: Int) {

        val item = orderList[position]
        holder.bind(item)


        //클릭 이벤트
        holder.btn_write_review.setOnClickListener {

            itemClickListener.invoke(position)
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var orderListImage : ImageView = view.findViewById(R.id.imageview_store)
        var orderListTime : TextView = view.findViewById(R.id.textview_time)
        var orderListPrice : TextView = view.findViewById(R.id.textview_order_price)
        var orderListMenu : TextView = view.findViewById(R.id.textview_order_list_menu)
        var btn_write_review : MaterialCardView = view.findViewById(R.id.btn_write_review)


        fun bind(item: OrderListResponseModel) {

            orderListTime.text = item.orderListTime
            orderListPrice.text = item.orderListPrice
            orderListMenu.text = item.orderListMenu

        }
    }
}