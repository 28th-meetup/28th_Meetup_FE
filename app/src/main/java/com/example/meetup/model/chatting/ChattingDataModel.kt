package com.example.meetup.model.chatting

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ChattingDataModel (
    @SerializedName("senderName") var senderName : String,
    @SerializedName("roomId") var roomId : String,
    @SerializedName("message") var message  :String,
//    @SerializedName("sendTime") var sendTime : String

)