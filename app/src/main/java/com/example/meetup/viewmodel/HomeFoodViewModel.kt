package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meetup.model.BestSellingFoodList
import com.example.meetup.model.CategoryIdResponseModel
import com.example.meetup.model.Food
import com.example.meetup.model.HomeResponseModel
import com.example.meetup.model.RecentSetFoodList
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFoodViewModel : ViewModel() {

    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    var topFoodInfoList = MutableLiveData<MutableList<BestSellingFoodList>>()
    var setFoodInfoList = MutableLiveData<MutableList<RecentSetFoodList>>()
    var regionId = MutableLiveData<Int>()


    init {
        topFoodInfoList.value = mutableListOf<BestSellingFoodList>()
        setFoodInfoList.value = mutableListOf<RecentSetFoodList>()
    }

    fun getHomeFoodInfo(context: Context) {

        var tempTopList = mutableListOf<BestSellingFoodList>()
        var tempSetList = mutableListOf<RecentSetFoodList>()

        var tokenManager = TokenManager(context)

        APIS.getHomeFood(tokenManager.getAccessToken().toString()).enqueue(object :
            Callback<HomeResponseModel> {
            override fun onResponse(
                call: Call<HomeResponseModel>,
                response: Response<HomeResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: HomeResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())
                    for (i in 0 until result?.result!!.bestSellingFoodList.size) {
                        var id = result?.result!!.bestSellingFoodList.get(i).foodId
                        var name = result?.result!!.bestSellingFoodList.get(i).name
                        var storeId = result?.result!!.bestSellingFoodList.get(i).storeId
                        var storeName = result?.result!!.bestSellingFoodList.get(i).storeName
                        var dollarPrice = result?.result!!.bestSellingFoodList.get(i).dollarPrice
                        var canadaPrice = result?.result!!.bestSellingFoodList.get(i).canadaPrice
                        var image = result?.result!!.bestSellingFoodList.get(i).image
                        var avgRate = result?.result!!.bestSellingFoodList.get(i).avgRate

                        var topFood = BestSellingFoodList(id, name, storeId, storeName, dollarPrice, canadaPrice, image, avgRate)
                        tempTopList.add(topFood)
                    }

                    topFoodInfoList.value = tempTopList
                    Log.d("밋업", "${topFoodInfoList.value}")

                    for (i in 0 until result?.result!!.recentSetFoodList.size) {
                        var id = result?.result!!.recentSetFoodList.get(i).foodId
                        var name = result?.result!!.recentSetFoodList.get(i).name
                        var storeId = result?.result!!.recentSetFoodList.get(i).storeId
                        var storeName = result?.result!!.recentSetFoodList.get(i).storeName
                        var dollarPrice = result?.result!!.recentSetFoodList.get(i).dollarPrice
                        var canadaPrice = result?.result!!.recentSetFoodList.get(i).canadaPrice
                        var image = result?.result!!.recentSetFoodList.get(i).image
                        var avgRate = result?.result!!.recentSetFoodList.get(i).avgRate

                        var setFood = RecentSetFoodList(id, name, storeId, storeName, dollarPrice, canadaPrice, image, avgRate)
                        tempSetList.add(setFood)
                    }

                    setFoodInfoList.value = tempSetList
                    Log.d("밋업", "${setFoodInfoList.value}")

                    regionId.value = result.result!!.globalRegionId.toInt()
                    Log.d("밋업", "${regionId.value}")

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        Toast.makeText(context, "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<HomeResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}