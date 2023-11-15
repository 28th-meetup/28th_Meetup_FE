package com.example.meetup.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.RowSellerOrderInProgressBinding
import com.example.meetup.dialog.DialogOrderCancel
import com.example.meetup.fragment.StoreEnrollDialogFragment

class OrderInProgressAdapter() : RecyclerView.Adapter<OrderInProgressAdapter.OrderInProgressViewHolder>() {
        private var onItemClickListener: ((Int) -> Unit)? = null
        private var context: Context? = null

        fun setOnItemClickListener(listener: (Int) -> Unit) {
            onItemClickListener = listener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderInProgressViewHolder {
            context = parent.context
            val binding =
                RowSellerOrderInProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return OrderInProgressViewHolder(binding)
        }

        override fun onBindViewHolder(holder: OrderInProgressViewHolder, position: Int) {
            holder.orderName.text = ""
        }

        override fun getItemCount() = 10

        inner class OrderInProgressViewHolder(val binding: RowSellerOrderInProgressBinding) : RecyclerView.ViewHolder(binding.root) {
            val orderName = binding.textviewOrderName
            val foodName = binding.textviewFoodName
            val optionName = binding.textviewOptionName
            val time = binding.textviewOrderTime
            val address = binding.textviewOrderAddress
            val deliveryOption = binding.buttonDeliveryOption

            init {
                binding.root.setOnClickListener {

                }

                binding.buttonOrderCancel.setOnClickListener {
//                    val dialog = DialogOrderCancel()
//                    // 알림창이 띄워져있는 동안 배경 클릭 막기
//                    dialog.isCancelable = false
//                    activity?.let { dialog.show(it.supportFragmentManager, "OrderDialog") }
                }
            }
        }
}