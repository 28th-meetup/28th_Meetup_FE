package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetup.model.GetHeartListResponseModel
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeartViewModel:ViewModel() {

    private lateinit var API : APIS
    private var _heartList = MutableLiveData<GetHeartListResponseModel>()
    var heartList : LiveData<GetHeartListResponseModel> = _heartList

    fun getHeartList (context: Context) {
        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

        val tokenManager = com.example.meetup.sharedPreference.TokenManager(context)   //가게 목록 가져오기

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

        Log.d("tokenManager", tokenManager.getAccessToken().toString())
        viewModelScope.launch {
            try{
                API.getHeartList(tokenManager.getAccessToken().toString()).enqueue(
                    object : Callback<GetHeartListResponseModel> {

                        override fun onResponse(call: Call<GetHeartListResponseModel>, response: Response<GetHeartListResponseModel>) {
                            if (response.isSuccessful) {

//                                _storeList.value = response.body()?.result?.stores
                                _heartList.value = response.body()


                                Log.d("GetHeartListResponseModel : " , " success , ${response.body().toString()}")
                            } else {

                                Log.d("GetHeartListResponseModel Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                            }
                        }

                        override fun onFailure(call: Call<GetHeartListResponseModel>, t: Throwable) {
                            Log.d("GetHeartListResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                        }
                    })
            } catch (e:Exception) {
                Log.d("GetHeartListResponseModel response : ", " fail 3 , ${e.message}")
            }
        }
    }
}