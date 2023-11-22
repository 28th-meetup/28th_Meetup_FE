package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.model.GetOrderListResponseModelResult
import com.example.meetup.model.OrderListResponseModel
import com.google.android.material.card.MaterialCardView


class OrderListAdapter(
    private var orderList: ArrayList<GetOrderListResponseModelResult>,
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
        var textview_order_list_menu_1 : TextView = view.findViewById(R.id.textview_order_list_menu_1)
        var textview_order_list_menu_1_count : TextView = view.findViewById(R.id.textview_order_list_menu_1_count)
        var textview_order_list_menu_2 : TextView = view.findViewById(R.id.textview_order_list_menu_2)
        var textview_order_list_menu_2_count : TextView = view.findViewById(R.id.textview_order_list_menu_2_count)


        var btn_write_review : MaterialCardView = view.findViewById(R.id.btn_write_review)

        var textview_store_name : TextView = view.findViewById(R.id.textview_store_name)

        fun bind(item: GetOrderListResponseModelResult) {

            Glide.with(itemView.context)
                .load(item.storeImage)
                .into(orderListImage)

            orderListPrice.text = item.totalPrice.toString()
            textview_order_list_menu_1.text = item.orderFoodDetailList[0].foodName
            textview_order_list_menu_1_count.text = item.orderFoodDetailList[0].orderCount.toString()
            textview_order_list_menu_2.text = item.orderFoodDetailList[1].foodName
            textview_order_list_menu_2_count.text = item.orderFoodDetailList[1].orderCount.toString()

        }
    }
}