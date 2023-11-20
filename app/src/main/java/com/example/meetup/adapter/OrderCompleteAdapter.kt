package com.example.meetup.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.databinding.RowSellerOrderCompleteBinding
import com.example.meetup.databinding.RowSellerOrderHistoryBinding
import com.example.meetup.databinding.RowSellerOrderInProgressBinding
import com.example.meetup.dialog.DialogOrderCancel
import com.example.meetup.model.OrderPreviewResponseList

class OrderCompleteAdapter(var orderHistory: List<OrderPreviewResponseList>) : RecyclerView.Adapter<OrderCompleteAdapter.OrderCompleteViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderCompleteViewHolder {
        context = parent.context
        val binding =
            RowSellerOrderCompleteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return OrderCompleteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderCompleteViewHolder, position: Int) {
        if (orderHistory.get(position).selectedOption == "delivery") {
            holder.layout.setBackgroundResource(R.color.sub_color)
            holder.deliveryOption.text = "[배달] "
        } else {
            holder.layout.setBackgroundResource(R.color.black)
            holder.deliveryOption.text = "[포장] "
        }
        holder.orderName.text = "주문자 : ${orderHistory.get(position).userName}"
        holder.time.text = "${orderHistory.get(position).orderedAt}"
        holder.address.text = "${orderHistory.get(position).addressAndPostalCode}"
        holder.recyclerview.run {
            adapter = OrderCompleteFoodAdapter(position)

            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getItemCount() = orderHistory.size

    inner class OrderCompleteViewHolder(val binding: RowSellerOrderCompleteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val deliveryOption = binding.textviewDeliveryOption
        val orderName = binding.textviewOrderName
        val time = binding.textviewOrderTime
        val address = binding.textviewOrderAddress
        val layout = binding.layoutOrder
        val recyclerview = binding.recyclerviewFood
    }

    inner class OrderCompleteFoodAdapter(var foodPosition: Int) :
        RecyclerView.Adapter<OrderCompleteFoodAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowSellerOrderHistoryBinding) :
            RecyclerView.ViewHolder(rowBinding.root) {

            val rowFoodName: TextView
            val rowOptionName: TextView
            val rowOptionCount: TextView

            init {
                rowFoodName = rowBinding.textviewFoodName
                rowOptionName = rowBinding.textviewOptionName
                rowOptionCount = rowBinding.textviewCount
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowFoodBinding = RowSellerOrderHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            val viewHolder = ViewHolderClass(rowFoodBinding)

            rowFoodBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolder
        }

        override fun getItemCount(): Int {
            return orderHistory.get(foodPosition).orderDetailPreviewList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            Log.d(
                "밋업",
                "${position} : ${orderHistory.get(foodPosition).orderDetailPreviewList.get(position)}"
            )
            holder.rowOptionCount.text =
                "[개수] ${orderHistory.get(foodPosition).orderDetailPreviewList.get(position).orderCount}개"
            holder.rowOptionName.text =
                "${orderHistory.get(foodPosition).orderDetailPreviewList.get(position).foodOptionName}"
            holder.rowFoodName.text = "${position + 1}. ${
                orderHistory.get(foodPosition).orderDetailPreviewList.get(position).foodName
            }"
        }
    }
}