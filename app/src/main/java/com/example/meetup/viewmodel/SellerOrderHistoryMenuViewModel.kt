package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meetup.model.OrderDetailPreviewList
import com.example.meetup.model.OrderPreviewResponseList
import com.example.meetup.model.ProcessingFoodDetailList
import com.example.meetup.model.ProcessingFoodList
import com.example.meetup.model.SellerOrderHistoryMenuResponseModel
import com.example.meetup.model.SellerOrderHistoryResponseModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerOrderHistoryMenuViewModel : ViewModel() {
    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    var orderHistoryList = MutableLiveData<MutableList<ProcessingFoodList>>()
    var orderCount = MutableLiveData<Long>()


    init {
        orderHistoryList.value = mutableListOf<ProcessingFoodList>()
    }

    fun getSellerOrderHistory(context: Context) {

        var tempZeroList = mutableListOf<ProcessingFoodList>()
        var tempPreviewList = mutableListOf<ProcessingFoodList>()
        var tokenManager = TokenManager(context)

        APIS.getSellerOrderHistoryMenu(tokenManager.getAccessToken().toString()).enqueue(object :
            Callback<SellerOrderHistoryMenuResponseModel> {
            override fun onResponse(
                call: Call<SellerOrderHistoryMenuResponseModel>,
                response: Response<SellerOrderHistoryMenuResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: SellerOrderHistoryMenuResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())
                    if(result?.result!!.processingFoodList.size == 0) {
                        Toast.makeText(context, "해당 카테고리에 해당하는 메뉴가 없습니다.", Toast.LENGTH_SHORT).show()
                        orderCount.value = 0
                        orderHistoryList.value = tempZeroList
                    } else {
                        orderCount.value = result.result!!.orderCount
                        for (i in 0 until result.result!!.processingFoodList.size) {
                            var tempDetailList = mutableListOf<ProcessingFoodDetailList>()
                            var foodId = result?.result!!.processingFoodList.get(i).foodId
                            var foodName = result?.result!!.processingFoodList.get(i).foodName
                            var totalOrderCount = result?.result!!.processingFoodList.get(i).totalOrderCount
                            for (j in 0 until result.result!!.processingFoodList.get(i).processingFoodDetailList.size) {
                                var foodOptionId = result?.result!!.processingFoodList.get(i).processingFoodDetailList.get(j).foodOptionId
                                var foodOptionName = result?.result!!.processingFoodList.get(i).processingFoodDetailList.get(j).foodOptionName
                                var orderCount = result?.result!!.processingFoodList.get(i).processingFoodDetailList.get(j).orderCount

                                var detail = ProcessingFoodDetailList(foodOptionId, foodOptionName, orderCount)
                                tempDetailList.add(detail)
                            }

                            var orderHistory = ProcessingFoodList(foodId, foodName, totalOrderCount, tempDetailList)

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

            override fun onFailure(call: Call<SellerOrderHistoryMenuResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}