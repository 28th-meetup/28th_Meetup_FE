package com.example.meetup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meetup.model.chatting.ChatListResponseModel
import com.example.meetup.model.chatting.ChattingDataModel
import org.json.JSONObject

class ChattingRoomViewModel:ViewModel() {
    private var _chattingData = MutableLiveData<ArrayList<ChattingDataModel>>()
    var chattingData : LiveData<ArrayList<ChattingDataModel>> = _chattingData

     var _roomId = MutableLiveData<String>()
    var roomId : LiveData<String> = _roomId

     var _senderName = MutableLiveData<String>()
    var senderName : LiveData<String> = _senderName


    fun addData ( A :ArrayList<ChattingDataModel> ){
        _chattingData.value= A

    }


}