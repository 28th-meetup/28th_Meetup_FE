package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetup.model.chatting.ChatListResponseModel
import com.example.meetup.model.chatting.ChatListResult
import com.example.meetup.model.store.GetStoreListStores
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChattingViewModel :ViewModel() {
    private lateinit var API : APIS

    private var _chatList = MutableLiveData<ChatListResponseModel>()
    var chatList : LiveData<ChatListResponseModel> = _chatList

    fun getChatList(context : Context) {
        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

        val tokenManager = com.example.meetup.sharedPreference.TokenManager(context)

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

        Log.d("tokenManager", tokenManager.getAccessToken().toString())
        viewModelScope.launch {
            try{
                API.getChatList(tokenManager.getAccessToken().toString()).enqueue(
                    object : Callback<ChatListResponseModel> {

                        override fun onResponse(call: Call<ChatListResponseModel>, response: Response<ChatListResponseModel>) {
                            if (response.isSuccessful) {

                                _chatList.value = response.body()

                                Log.d("ChatListResult : " , " success , ${response.body().toString()}")
                            } else {

                                Log.d("ChatListResult Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                            }
                        }

                        override fun onFailure(call: Call<ChatListResponseModel>, t: Throwable) {
                            Log.d("ChatListResult Response : ", " fail 2 , ${t.message.toString()}")
                        }
                    })
            } catch (e:Exception) {
                Log.d("ChatListResult response : ", " fail 3 , ${e.message}")
            }
        }
    }
}