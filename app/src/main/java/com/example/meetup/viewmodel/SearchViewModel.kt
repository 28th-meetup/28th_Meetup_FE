package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meetup.model.ProcessingFoodList
import com.example.meetup.model.SearchFood
import com.example.meetup.model.SearchResponseModel
import com.example.meetup.model.StoreDto
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    var searchStoreList = MutableLiveData<MutableList<StoreDto>>()
    var searchFoodList = MutableLiveData<MutableList<SearchFood>>()
    var isExist = MutableLiveData<Boolean>()


    init {
        searchStoreList.value = mutableListOf<StoreDto>()
        searchFoodList.value = mutableListOf<SearchFood>()
    }

    fun getSearchList(context: Context, keyword: String) {

        var tempZeroList = mutableListOf<ProcessingFoodList>()
        var tempStoreList = mutableListOf<StoreDto>()
        var tempFoodList = mutableListOf<SearchFood>()

        var tokenManager = TokenManager(context)

        APIS.search(tokenManager.getAccessToken().toString(), keyword, "bookmark", "ASC").enqueue(object :
            Callback<SearchResponseModel> {
            override fun onResponse(
                call: Call<SearchResponseModel>,
                response: Response<SearchResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: SearchResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())
                    // 가게 검색 결과
                    for (i in 0 until result?.result!!.stores.size) {
                        var tempImageList = mutableListOf<String>()
                        var storeId = result?.result!!.stores.get(i).storeDto.id
                        var storeName = result?.result!!.stores.get(i).storeDto.name
                        var description = result?.result!!.stores.get(i).storeDto.description
                        var iskoreanStore = result?.result!!.stores.get(i).storeDto.koreanYn
                        var avgRate = result?.result!!.stores.get(i).storeDto.avgRate
                        var minOrderAmount = result?.result!!.stores.get(i).storeDto.minOrderAmount
                        for(j in 0 until result?.result!!.stores.get(i).storeDto.images.size) {
                            var images = result?.result!!.stores.get(i).storeDto.images.get(j)
                            tempImageList.add(images)
                        }

                        var searchStore = StoreDto(storeId, storeName, description, iskoreanStore, avgRate, minOrderAmount, tempImageList)

                        tempStoreList.add(searchStore)
                    }
                    searchStoreList.value = tempStoreList
                    Log.d("밋업", "${searchStoreList.value}")

                    // 음식 검색 결과
                    for (i in 0 until result?.result!!.foods.size) {
                        var foodId = result?.result!!.foods.get(i).foodId
                        var foodName = result?.result!!.foods.get(i).name
                        var storeId = result?.result!!.foods.get(i).storeId
                        var storeName = result?.result!!.foods.get(i).storeName
                        var dollarPrice = result?.result!!.foods.get(i).dollarPrice
                        var canadaPrice = result?.result!!.foods.get(i).canadaPrice
                        var image = result?.result!!.foods.get(i).image
                        var avgRate = result?.result!!.foods.get(i).avgRate

                        var searchFood = SearchFood(foodId, foodName, storeId, storeName, dollarPrice, canadaPrice, image, avgRate)

                        tempFoodList.add(searchFood)
                    }
                    searchFoodList.value = tempFoodList
                    Log.d("밋업", "${searchFoodList.value}")

                    isExist.value =
                        !(searchFoodList.value!!.size == 0 && searchStoreList.value!!.size == 0)
                    Log.d("밋업", "${isExist.value}")
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        Toast.makeText(context, "존재하지 않는 가게입니다.", Toast.LENGTH_SHORT).show()
                        isExist.value = false
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}