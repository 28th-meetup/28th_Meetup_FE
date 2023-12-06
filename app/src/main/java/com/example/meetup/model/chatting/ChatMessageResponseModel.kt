package com.example.meetup.model.chatting

data class ChatMessageResponseModel(

    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: ArrayList<ChatMessageResponseModelResult>,
)

data class ChatMessageResponseModelResult(
    val senderName: String,
    val roomId: String,
    val message: String,
)

