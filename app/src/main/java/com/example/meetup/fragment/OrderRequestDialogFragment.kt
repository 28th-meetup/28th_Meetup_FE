package com.example.meetup.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.meetup.R
import com.example.meetup.databinding.ActivityAuthBinding
import com.example.meetup.databinding.DialogSignUpBinding
import com.example.meetup.databinding.FragmentOrderRequestDialogBinding
import com.example.meetup.dialog.SignUpDialogInterface
import com.example.meetup.model.BasicResponseModel
import com.example.meetup.model.OrderPreviewResponseList
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.TokenManager
import com.example.meetup.viewmodel.SellerOrderHistoryViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRequestDialogFragment(var activity: ViewModelStoreOwner, var orderHistory: List<OrderPreviewResponseList>) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: FragmentOrderRequestDialogBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: SignUpDialogInterface? = null
    lateinit var viewModel: SellerOrderHistoryViewModel

    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderRequestDialogBinding.inflate(inflater)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        viewModel = ViewModelProvider(activity)[SellerOrderHistoryViewModel::class.java]


        binding.run {
            textviewMenuName.text = orderHistory.get(0).orderDetailPreviewList.get(0).foodName
            textviewTime.text = orderHistory.get(0).orderedAt
            textviewLocation.text = orderHistory.get(0).addressAndPostalCode
            textviewOptionName.text = orderHistory.get(0).orderDetailPreviewList.get(0).foodOptionName
        }

        binding.buttonAccepted.setOnClickListener {
            acceptOrder()
            viewModel.clearList()
        }

        binding.buttonRejected.setOnClickListener {
            cancelOrder()
            viewModel.clearList()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        var activityAuthBinding = ActivityAuthBinding.inflate(layoutInflater)

        activityAuthBinding.root.setBackgroundResource(R.drawable.blur_background)
    }

    fun cancelOrder() {

        var tokenManager = TokenManager(requireContext())

        APIS.setOrderStatus(tokenManager.getAccessToken().toString(), orderHistory.get(0).orderId.toInt(), "rejected").enqueue(object :
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

    fun acceptOrder() {

        var tokenManager = TokenManager(requireContext())

        APIS.setOrderStatus(tokenManager.getAccessToken().toString(), orderHistory.get(0).orderId.toInt(), "accepted").enqueue(object :
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