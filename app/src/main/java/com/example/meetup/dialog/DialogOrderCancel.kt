package com.example.meetup.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.ActivityHomeBinding
import com.example.meetup.databinding.DialogSellerOrderCancelBinding
import com.example.meetup.model.BasicResponseModel
import com.example.meetup.model.OrderPreviewResponseList
import com.example.meetup.model.SignInResponseModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.TokenManager
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface OrderCancelDialogInterface {
    fun onClickYesButton(id: Int)
}
class DialogOrderCancel(var manager: FragmentManager, var orderHistory: OrderPreviewResponseList) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogSellerOrderCancelBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: OrderCancelDialogInterface? = null

    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSellerOrderCancelBinding.inflate(inflater)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding.run {
            if(orderHistory.orderDetailPreviewList.size > 1) {
                textviewOrderCancelFoodName.text = orderHistory.orderDetailPreviewList.get(0).foodName + " 외 ${orderHistory.orderDetailPreviewList.size-1}개"
            } else {
                textviewOrderCancelFoodName.text = orderHistory.orderDetailPreviewList.get(0).foodName
            }
            textviewOrderCancelTime.text = orderHistory.orderedAt
            textviewOrderCancelAddress.text = orderHistory.addressAndPostalCode
        }

        binding.buttonOrderCancel.setOnClickListener {
            cancelOrder()
        }


        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        var activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)

        activityHomeBinding.root.setBackgroundResource(R.drawable.blur_background)
    }

    fun cancelOrder() {

        var tokenManager = TokenManager(requireContext())

        APIS.setOrderStatus(tokenManager.getAccessToken().toString(), orderHistory.orderId.toInt(), "rejected").enqueue(object :
            Callback<BasicResponseModel> {
            override fun onResponse(
                call: Call<BasicResponseModel>,
                response: Response<BasicResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: BasicResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())
                    dismiss()
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())

                    if (response.code() == 400) {
                        Snackbar.make(
                            binding.root,
                            "다시 시도해주세요.",
                            Snackbar.LENGTH_LONG
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