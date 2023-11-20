package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetup.model.store.GetStoreListResponseModel
import com.example.meetup.model.store.GetStoreMenuResponseModel
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreDetailMenuViewModel:ViewModel() {
    private lateinit var API : APIS

    private var _storeDetailMenuList = MutableLiveData<GetStoreMenuResponseModel>()
    var storeDetailMenuList : LiveData<GetStoreMenuResponseModel> = _storeDetailMenuList

    fun getStoreMenu(context : Context,storeId : Int ) {
        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

        val tokenManager = com.example.meetup.sharedPreference.TokenManager(context)   //가게 목록 가져오기

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

        Log.d("tokenManager", tokenManager.getAccessToken().toString())
        viewModelScope.launch {
            try{
                API.getStoreMenu(tokenManager.getAccessToken().toString(),storeId).enqueue(
                    object : Callback<GetStoreMenuResponseModel> {

                        override fun onResponse(call: Call<GetStoreMenuResponseModel>, response: Response<GetStoreMenuResponseModel>) {
                            if (response.isSuccessful) {

//                                _storeList.value = response.body()?.result?.stores

                                _storeDetailMenuList.value = response.body()

                                Log.d("GetStoreMenuResponseModel : " , " success , ${response.body().toString()}")
                            } else {

                                Log.d("GetStoreMenuResponseModel Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                            }
                        }

                        override fun onFailure(call: Call<GetStoreMenuResponseModel>, t: Throwable) {
                            Log.d("GetStoreMenuResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                        }
                    })
            } catch (e:Exception) {
                Log.d("GetStoreMenuResponseModel response : ", " fail 3 , ${e.message}")
            }
        }
    }
}