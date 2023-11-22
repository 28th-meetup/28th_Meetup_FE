package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meetup.R
import com.example.meetup.fragment.MenuFragment
import com.example.meetup.model.FoodIdResponseModel
import com.example.meetup.model.FoodIdResult
import com.example.meetup.model.FoodOptionResponseList
import com.example.meetup.model.MenuOptionResponseModel
import com.example.meetup.model.MenuOptionResult
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodMenuDetailViewModel : ViewModel() {
    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    var foodMenuInfoList = MutableLiveData<MutableList<FoodIdResult>>()
    var foodMenuOptionInfoList = MutableLiveData<MutableList<MenuOptionResult>>()


    init {
        foodMenuInfoList.value = mutableListOf<FoodIdResult>()
        foodMenuOptionInfoList.value = mutableListOf<MenuOptionResult>()
    }

    fun getFoodMenuInfo(context: Context, manager: FragmentManager, foodId: Int) {
        var tempList = mutableListOf<FoodIdResult>()
        var tempOptionList = mutableListOf<FoodOptionResponseList>()

        var tokenManager = TokenManager(context)

        APIS.getFoodMenu(tokenManager.getAccessToken().toString(), foodId).enqueue(object :
            Callback<FoodIdResponseModel> {
            override fun onResponse(
                call: Call<FoodIdResponseModel>,
                response: Response<FoodIdResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: FoodIdResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    var id = result?.result!!.id
                    var storeId = result?.result!!.storeId
                    var storeName = result?.result!!.storeName
                    var categoryId = result?.result!!.categoryId
                    var name = result?.result!!.name
                    var dollarPrice = result?.result!!.dollarPrice
                    var canadaPrice = result?.result!!.canadaPrice
                    var image = result?.result!!.image
                    var description = result?.result!!.description
                    var foodPackage = result?.result!!.foodPackage
                    var informationDescription = result?.result!!.informationDescription
                    var ingredient = result?.result!!.ingredient
                    var minOrderPrice = result?.result!!.minOrderAmount

                    for (i in 0 until result?.result!!.foodOptionResponseList.size) {
                        var id = result?.result!!.foodOptionResponseList.get(i).id
                        var name = result?.result!!.foodOptionResponseList.get(i).name
                        var dollarPrice = result?.result!!.foodOptionResponseList.get(i).dollarPrice
                        var canadaPrice = result?.result!!.foodOptionResponseList.get(i).canadaPrice

                        var option1 = FoodOptionResponseList(id, name, dollarPrice, canadaPrice)
                        tempOptionList.add(option1)
                    }

                    var food = FoodIdResult(id, storeId, storeName, categoryId, name, dollarPrice, canadaPrice, image, description, foodPackage, informationDescription, ingredient, minOrderPrice, tempOptionList)
                    tempList.add(food)

                    foodMenuInfoList.value = tempList

                    Log.d("밋업", "${foodMenuInfoList.value}")

                    val menuFragment = MenuFragment()

                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.frameArea, menuFragment)
                    transaction.addToBackStack("")
                    transaction.commit()

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        Toast.makeText(context, "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<FoodIdResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }

    fun getFoodMenuOptionList(context: Context, foodId: Int) {
        var tempList = mutableListOf<MenuOptionResult>()
        var tokenManager = TokenManager(context)

        APIS.getFoodMenuOption(tokenManager.getAccessToken().toString(), foodId).enqueue(object :
            Callback<MenuOptionResponseModel> {
            override fun onResponse(
                call: Call<MenuOptionResponseModel>,
                response: Response<MenuOptionResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: MenuOptionResponseModel? = response?.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    for(i in 0 until result?.result!!.size) {
                        var id = result?.result!!.get(i).id
                        var name = result?.result!!.get(i).name
                        var dollarPrice = result?.result!!.get(i).dollarPrice
                        var canadaPrice = result?.result!!.get(i).canadaPrice

                        var food = MenuOptionResult(id, name, dollarPrice, canadaPrice)

                        tempList.add(food)
                    }

                    foodMenuOptionInfoList.value = tempList

                    Log.d("밋업", "${foodMenuOptionInfoList.value}")
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        Toast.makeText(context, "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<MenuOptionResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}