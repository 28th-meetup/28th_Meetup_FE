package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetup.model.GetOrderListResponseModel
import com.example.meetup.model.GetOrderListResponseModelOrderFoodDetailList
import com.example.meetup.model.GetOrderListResponseModelResult
import com.example.meetup.model.chatting.ChattingDataModel
import com.example.meetup.model.store.GetStoreDetailResponseModel
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListViewModel :ViewModel() {

    private lateinit var API : APIS

    private var _orderList = MutableLiveData<GetOrderListResponseModel>()
    var orderList : LiveData<GetOrderListResponseModel> = _orderList

    private var _storeId = MutableLiveData<Long>()
    var storeId : LiveData<Long> = _storeId


    fun getOrderList(context : Context) {
        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

        val tokenManager = com.example.meetup.sharedPreference.TokenManager(context)   //가게 목록 가져오기

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

        Log.d("tokenManager", tokenManager.getAccessToken().toString())
        viewModelScope.launch {
            try{
                API.getOrderList(tokenManager.getAccessToken().toString()).enqueue(
                    object : Callback<GetOrderListResponseModel> {

                        override fun onResponse(call: Call<GetOrderListResponseModel>, response: Response<GetOrderListResponseModel>) {
                            if (response.isSuccessful) {

                                _orderList.value = response.body()


                                Log.d("_orderList : " , " success , ${_orderList.value}")

                                Log.d("GetOrderListResponseModel : " , " success , ${response.body().toString()}")
                            } else {

                                Log.d("GetOrderListResponseModel Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                            }
                        }

                        override fun onFailure(call: Call<GetOrderListResponseModel>, t: Throwable) {
                            Log.d("GetOrderListResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                        }
                    })
            } catch (e:Exception) {
                Log.d("GetOrderListResponseModel response : ", " fail 3 , ${e.message}")
            }
        }
    }


    fun setStoreId(a : Long){

        _storeId.value = a
    }
}