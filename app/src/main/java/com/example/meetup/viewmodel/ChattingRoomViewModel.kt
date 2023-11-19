package com.example.meetup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meetup.model.chatting.ChatListResponseModel
import com.example.meetup.model.chatting.ChattingDataModel

class ChattingRoomViewModel:ViewModel() {
    private var _chattingData = MutableLiveData<ArrayList<ChattingDataModel>>()
    var chattingData : LiveData<ArrayList<ChattingDataModel>> = _chattingData


    fun addData ( A :ChattingDataModel ){
        _chattingData.value!!.add(A)
    }

}