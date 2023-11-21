package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meetup.R
import com.example.meetup.dialog.DialogSignUp
import com.example.meetup.fragment.SignUpAddressFragment
import com.example.meetup.model.CategoryIdResponseModel
import com.example.meetup.model.Food
import com.example.meetup.model.SignUpResponseModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.sharedPreference.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFoodViewModel : ViewModel() {

    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    var categoryFoodInfoList = MutableLiveData<MutableList<Food>>()


    init {
        categoryFoodInfoList.value = mutableListOf<Food>()
    }

    fun getCategoryFoodInfo(context: Context, sorting: String, categoryId : Int) {

        var tempList = mutableListOf<Food>()
        var tempZeroList = mutableListOf<Food>()
        var tokenManager = TokenManager(context)

        APIS.getCategoryFood(tokenManager.getAccessToken().toString(), categoryId, sorting).enqueue(object :
            Callback<CategoryIdResponseModel> {
            override fun onResponse(
                call: Call<CategoryIdResponseModel>,
                response: Response<CategoryIdResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: CategoryIdResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())
                    if(result?.result!!.size == 0) {
                        Toast.makeText(context, "해당 카테고리에 해당하는 메뉴가 없습니다.", Toast.LENGTH_SHORT).show()
                        categoryFoodInfoList.value = tempZeroList
                    } else {
                        for (i in 0 until result.result!!.size) {
                            var id = result?.result!!.get(i).foodId
                            var name = result?.result!!.get(i).name
                            var storeId = result?.result!!.get(i).storeId
                            var storeName = result?.result!!.get(i).storeName
                            var dollarPrice = result?.result!!.get(i).dollarPrice
                            var canadaPrice = result?.result!!.get(i).canadaPrice
                            var image = result?.result!!.get(i).image
                            var avgRate = result?.result!!.get(i).avgRate

                            var food = Food(id, name, storeId, storeName, dollarPrice, canadaPrice, image, avgRate)

                            tempList.add(food)
                        }

                        categoryFoodInfoList.value = tempList

                        Log.d("밋업", "${categoryFoodInfoList.value}")
                    }
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        Toast.makeText(context, "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<CategoryIdResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}