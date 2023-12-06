package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetup.model.GetChattingMessage
import com.example.meetup.model.GetChattingMessageResult
import com.example.meetup.model.chatting.ChatListResponseModel
import com.example.meetup.model.chatting.ChatMessageResponseModel
import com.example.meetup.model.chatting.ChatMessageResponseModelResult
import com.example.meetup.model.chatting.ChattingDataModel
import com.example.meetup.model.store.GetStoreListResponseModel
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChattingRoomViewModel : ViewModel() {

    private lateinit var API : APIS

    private var _chattingData = MutableLiveData<ArrayList<ChattingDataModel>>()
    var chattingData: LiveData<ArrayList<ChattingDataModel>> = _chattingData

    private var _chattingMessageList = MutableLiveData<ArrayList<ChatMessageResponseModelResult>>()
    var chattingMessageList: LiveData<ArrayList<ChatMessageResponseModelResult>> = _chattingMessageList


    var _roomId = MutableLiveData<String>()
    var roomId: LiveData<String> = _roomId

    var _senderName = MutableLiveData<String>()
    var senderName: LiveData<String> = _senderName

    var chat = ArrayList<GetChattingMessageResult>()


    fun addData(A: ArrayList<ChattingDataModel>) {
        _chattingData.value = A

    }

    fun getChattingMessage(context : Context, roomId :String) {

        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

        val tokenManager = com.example.meetup.sharedPreference.TokenManager(context)   //가게 목록 가져오기

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

        Log.d("tokenManager", tokenManager.getAccessToken().toString())
        viewModelScope.launch {
            try{
                API.getChattingMessage(tokenManager.getAccessToken().toString(),roomId).enqueue(
                    object : Callback<ChatMessageResponseModel> {

                        override fun onResponse(call: Call<ChatMessageResponseModel>, response: Response<ChatMessageResponseModel>) {
                            if (response.isSuccessful) {

//                                _storeList.value = response.body()?.result?.stores

                                _chattingMessageList.value = response.body()?.result

                                Log.d("_chattingMessageList : " , " success , ${_chattingMessageList.value}")

                                Log.d("ChatMessageResponseModel : " , " success , ${response.body().toString()}")
                            } else {

                                Log.d("ChatMessageResponseModel Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                            }
                        }

                        override fun onFailure(call: Call<ChatMessageResponseModel>, t: Throwable) {
                            Log.d("ChatMessageResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                        }
                    })
            } catch (e:Exception) {
                Log.d("ChatMessageResponseModel response : ", " fail 3 , ${e.message}")
            }
        }
    }


}