package com.example.meetup.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.databinding.RowOrderFoodBinding
import com.example.meetup.databinding.RowSellerOrderInProgressBinding
import com.example.meetup.databinding.RowSellerOrderMenuBinding
import com.example.meetup.databinding.RowSellerOrderOptionBinding
import com.example.meetup.model.ProcessingFoodList

class OrderMenuAdapter(var orderHistory: List<ProcessingFoodList>) : RecyclerView.Adapter<OrderMenuAdapter.OrderMenuViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderMenuViewHolder {
        context = parent.context
        val binding =
            RowSellerOrderMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderMenuViewHolder, position: Int) {
        holder.foodName.text = "${orderHistory.get(position).foodName}"
        holder.foodCount.text = "${orderHistory.get(position).totalOrderCount}개"
        holder.recyclerView.run {
            adapter = OrderMenuOptionRecyclerViewAdapter(position)

            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getItemCount() = orderHistory.size

    inner class OrderMenuViewHolder(val binding: RowSellerOrderMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val foodName = binding.textviewFoodName
        val foodCount = binding.textviewFoodCount
        val recyclerView = binding.recyclerviewOption
    }

    inner class OrderMenuOptionRecyclerViewAdapter(var foodPosition: Int) : RecyclerView.Adapter<OrderMenuOptionRecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowSellerOrderOptionBinding) :
            RecyclerView.ViewHolder(rowBinding.root) {

            val rowOptionName : TextView
            val rowOptionCount : TextView

            init {
                rowOptionName = rowBinding.textviewOptionName
                rowOptionCount = rowBinding.textviewOptionCount
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowFoodBinding = RowSellerOrderOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val viewHolder = ViewHolderClass(rowFoodBinding)

            rowFoodBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolder
        }

        override fun getItemCount(): Int {
            return orderHistory.get(foodPosition).processingFoodDetailList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            Log.d("밋업", "${position} : ${orderHistory.get(foodPosition).processingFoodDetailList.get(position)}")
            holder.rowOptionCount.text = "${orderHistory.get(foodPosition).processingFoodDetailList.get(position).orderCount}개"
            holder.rowOptionName.text = "${orderHistory.get(foodPosition).processingFoodDetailList.get(position).foodOptionName}"
        }
    }
}
