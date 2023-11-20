package com.example.meetup.model.chatting

import kotlinx.serialization.Serializable

@Serializable
data class ChattingDataModel (
    var senderName : String,
    var roomId : String,
    var message  :String,
    var sendTime : String

)