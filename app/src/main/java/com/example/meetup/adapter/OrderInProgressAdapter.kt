package com.example.meetup.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.databinding.RowSellerOrderHistoryBinding
import com.example.meetup.databinding.RowSellerOrderInProgressBinding
import com.example.meetup.dialog.DialogOrderCancel
import com.example.meetup.model.BasicResponseModel
import com.example.meetup.model.OrderPreviewResponseList
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.TokenManager
import com.example.meetup.viewmodel.SellerOrderHistoryViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderInProgressAdapter(var manager: FragmentManager, var activity: Activity, var lifecyclerOwner: ViewModelStoreOwner, var orderHistory: List<OrderPreviewResponseList>) : RecyclerView.Adapter<OrderInProgressAdapter.OrderInProgressViewHolder>() {
        private var onItemClickListener: ((Int) -> Unit)? = null
        private var context: Context? = null

        private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

        lateinit var viewModel: SellerOrderHistoryViewModel

        fun setOnItemClickListener(listener: (Int) -> Unit) {
            onItemClickListener = listener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderInProgressViewHolder {
            context = parent.context
            var binding =
                RowSellerOrderInProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            viewModel = ViewModelProvider(lifecyclerOwner)[SellerOrderHistoryViewModel::class.java]
            return OrderInProgressViewHolder(binding)
        }

        override fun onBindViewHolder(holder: OrderInProgressViewHolder, position: Int) {
            if(orderHistory.get(position).selectedOption == "delivery") {
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
                adapter = OrderInProgressFoodAdapter(position)

                layoutManager = LinearLayoutManager(context)
            }
        }

        override fun getItemCount() = orderHistory.size

        inner class OrderInProgressViewHolder(val binding: RowSellerOrderInProgressBinding) : RecyclerView.ViewHolder(binding.root) {
            val deliveryOption = binding.textviewDeliveryOption
            val orderName = binding.textviewOrderName
            val time = binding.textviewOrderTime
            val address = binding.textviewOrderAddress
            val layout = binding.layoutOrder
            val recyclerview = binding.recyclerviewFood

            init {
                binding.root.setOnClickListener {

                }

                binding.buttonOrderCancel.setOnClickListener {
                    val dialog = DialogOrderCancel(manager, orderHistory.get(adapterPosition))
                    // 알림창이 띄워져있는 동안 배경 클릭 막기
                    dialog.isCancelable = false
                    activity?.let { dialog.show(manager, "OrderDialog") }
                }

                binding.buttonOrderComplete.setOnClickListener {
                    //처리 완료
                    completeOrder(orderHistory.get(adapterPosition).orderId.toInt())
                }

                binding.buttonChatting.setOnClickListener {
                    // 채팅 화면으로 전환
                }
            }
        }

    inner class OrderInProgressFoodAdapter(var foodPosition: Int) : RecyclerView.Adapter<OrderInProgressFoodAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowSellerOrderHistoryBinding) :
            RecyclerView.ViewHolder(rowBinding.root) {

            val rowFoodName : TextView
            val rowOptionName : TextView
            val rowOptionCount : TextView

            init {
                rowFoodName = rowBinding.textviewFoodName
                rowOptionName = rowBinding.textviewOptionName
                rowOptionCount = rowBinding.textviewCount
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowFoodBinding = RowSellerOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            Log.d("밋업", "${position} : ${orderHistory.get(foodPosition).orderDetailPreviewList.get(position)}")
            holder.rowOptionCount.text = "[개수] ${orderHistory.get(foodPosition).orderDetailPreviewList.get(position).orderCount}개"
            holder.rowOptionName.text = "${orderHistory.get(foodPosition).orderDetailPreviewList.get(position).foodOptionName}"
            holder.rowFoodName.text = "${position+1}. ${orderHistory.get(foodPosition).orderDetailPreviewList.get(position).foodName}"
        }
    }

    fun completeOrder(orderId: Int) {

        var tokenManager = TokenManager(activity)

        APIS.setOrderStatus(tokenManager.getAccessToken().toString(), orderId, "completed").enqueue(object :
            Callback<BasicResponseModel> {
            override fun onResponse(
                call: Call<BasicResponseModel>,
                response: Response<BasicResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: BasicResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())
                    viewModel.getSellerOrderHistory(context!!, "pending")
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())

                    if (response.code() == 400) {
                        Toast.makeText(
                            activity,
                            "다시 시도해주세요.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}