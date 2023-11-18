package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetup.model.store.GetStoreListResponseModel
import com.example.meetup.model.store.GetStoreListStores
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import com.kakao.sdk.auth.TokenManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreListViewModel : ViewModel()
{
    private lateinit var API : APIS


    private var _storeList = MutableLiveData<ArrayList<GetStoreListStores>>()
    var storeList : LiveData<ArrayList<GetStoreListStores>> = _storeList



//val tokenManager = com.example.meetup.sharedPreference.TokenManager()
    //가게 목록 가져오기
    fun getStoreList(context : Context) {
        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

        val tokenManager = com.example.meetup.sharedPreference.TokenManager(context)   //가게 목록 가져오기

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

        Log.d("tokenManager", tokenManager.getAccessToken().toString())
        viewModelScope.launch {
            try{
                API.getStoreList(tokenManager.getAccessToken().toString()).enqueue(
                    object : Callback<ArrayList<GetStoreListStores>> {

                        override fun onResponse(call: Call<ArrayList<GetStoreListStores>>, response: Response<ArrayList<GetStoreListStores>>) {
                            if (response.isSuccessful) {

                                _storeList.value = response.body()

                                Log.d("GetStoreListResponseModel : " , " success , ${response.body().toString()}")
                            } else {

                                Log.d("GetStoreListResponseModel Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                            }
                        }

                        override fun onFailure(call: Call<ArrayList<GetStoreListStores>>, t: Throwable) {
                            Log.d("GetStoreListResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                        }
                    })
            } catch (e:Exception) {
                Log.d("GetStoreListResponseModel response : ", " fail 3 , ${e.message}")
            }
        }
    }

}