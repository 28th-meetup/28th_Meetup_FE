package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meetup.model.CategoryIdResponseModel
import com.example.meetup.model.Food
import com.example.meetup.model.OrderDetailPreviewList
import com.example.meetup.model.OrderHistoryResult
import com.example.meetup.model.OrderPreviewResponseList
import com.example.meetup.model.SellerOrderHistoryResponseModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerOrderHistoryViewModel : ViewModel() {
    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    var orderHistoryList = MutableLiveData<MutableList<OrderPreviewResponseList>>()
    var orderCount = MutableLiveData<Long>()


    init {
        orderHistoryList.value = mutableListOf<OrderPreviewResponseList>()
    }

    fun getSellerOrderHistory(context: Context, orderStatus : String) {

        var tempZeroList = mutableListOf<OrderPreviewResponseList>()
        var tempPreviewList = mutableListOf<OrderPreviewResponseList>()
        var tokenManager = TokenManager(context)

        APIS.getSellerOrderHistory(tokenManager.getAccessToken().toString(), orderStatus).enqueue(object :
            Callback<SellerOrderHistoryResponseModel> {
            override fun onResponse(
                call: Call<SellerOrderHistoryResponseModel>,
                response: Response<SellerOrderHistoryResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: SellerOrderHistoryResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())
                    if(result?.result!!.orderPreviewResponseList.size == 0) {
                        Toast.makeText(context, "해당하는 주문이 없습니다.", Toast.LENGTH_SHORT).show()
                        orderCount.value = 0
                        orderHistoryList.value = tempZeroList
                    } else {
                        orderCount.value = result.result!!.orderCount
                        for (i in 0 until result.result!!.orderPreviewResponseList.size) {
                            var tempDetailList = mutableListOf<OrderDetailPreviewList>()
                            var id = result?.result!!.orderPreviewResponseList.get(i).orderId
                            var userName = result?.result!!.orderPreviewResponseList.get(i).userName
                            var addressAndPostalCode = result?.result!!.orderPreviewResponseList.get(i).addressAndPostalCode
                            var detailAddress = result?.result!!.orderPreviewResponseList.get(i).detailAddress
                            var selectedOption = result?.result!!.orderPreviewResponseList.get(i).selectedOption
                            var orderedAt = result?.result!!.orderPreviewResponseList.get(i).orderedAt
                            for (j in 0 until result.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.size) {
                                var orderDeatailId = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).orderDetailId
                                var foodId = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).foodId
                                var foodName = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).foodName
                                var foodOptionId = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).foodOptionId
                                var foodOptionName = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).foodOptionName
                                var orderCount = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).orderCount

                                var detail = OrderDetailPreviewList(orderDeatailId, foodId, foodName, foodOptionId, foodOptionName, orderCount)
                                tempDetailList.add(detail)
                            }

                            var orderHistory = OrderPreviewResponseList(id, userName, addressAndPostalCode, detailAddress, selectedOption, orderedAt, tempDetailList)

                            tempPreviewList.add(orderHistory)
                        }

                        orderHistoryList.value = tempPreviewList

                        Log.d("밋업", "${orderHistoryList.value}")
                    }
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        Toast.makeText(context, "존재하지 않는 가게입니다.", Toast.LENGTH_SHORT).show()
                        orderCount.value = 0
                        orderHistoryList.value = tempZeroList
                    }
                }
            }

            override fun onFailure(call: Call<SellerOrderHistoryResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }

    fun getHomeSellerOrderHistory(context: Context, orderStatus : String) {

        var tempZeroList = mutableListOf<OrderPreviewResponseList>()
        var tempPreviewList = mutableListOf<OrderPreviewResponseList>()
        var tokenManager = TokenManager(context)

        APIS.getSellerOrderHistory(tokenManager.getAccessToken().toString(), orderStatus).enqueue(object :
            Callback<SellerOrderHistoryResponseModel> {
            override fun onResponse(
                call: Call<SellerOrderHistoryResponseModel>,
                response: Response<SellerOrderHistoryResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: SellerOrderHistoryResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())
                    if(result?.result!!.orderPreviewResponseList.size == 0) {
                        orderCount.value = 0
                        orderHistoryList.value = tempZeroList
                    } else {
                        orderCount.value = result.result!!.orderCount
                        for (i in 0 until result.result!!.orderPreviewResponseList.size) {
                            var tempDetailList = mutableListOf<OrderDetailPreviewList>()
                            var id = result?.result!!.orderPreviewResponseList.get(i).orderId
                            var userName = result?.result!!.orderPreviewResponseList.get(i).userName
                            var addressAndPostalCode = result?.result!!.orderPreviewResponseList.get(i).addressAndPostalCode
                            var detailAddress = result?.result!!.orderPreviewResponseList.get(i).detailAddress
                            var selectedOption = result?.result!!.orderPreviewResponseList.get(i).selectedOption
                            var orderedAt = result?.result!!.orderPreviewResponseList.get(i).orderedAt
                            for (j in 0 until result.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.size) {
                                var orderDeatailId = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).orderDetailId
                                var foodId = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).foodId
                                var foodName = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).foodName
                                var foodOptionId = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).foodOptionId
                                var foodOptionName = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).foodOptionName
                                var orderCount = result?.result!!.orderPreviewResponseList.get(i).orderDetailPreviewList.get(j).orderCount

                                var detail = OrderDetailPreviewList(orderDeatailId, foodId, foodName, foodOptionId, foodOptionName, orderCount)
                                tempDetailList.add(detail)
                            }

                            var orderHistory = OrderPreviewResponseList(id, userName, addressAndPostalCode, detailAddress, selectedOption, orderedAt, tempDetailList)

                            tempPreviewList.add(orderHistory)
                        }

                        orderHistoryList.value = tempPreviewList

                        Log.d("밋업", "${orderHistoryList.value}")
                    }
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        Toast.makeText(context, "존재하지 않는 가게입니다.", Toast.LENGTH_SHORT).show()
                        orderCount.value = 0
                        orderHistoryList.value = tempZeroList
                    }
                }
            }

            override fun onFailure(call: Call<SellerOrderHistoryResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}