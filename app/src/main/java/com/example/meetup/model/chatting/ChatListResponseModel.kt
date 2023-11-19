package com.example.meetup.model.chatting

data class ChatListResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    var message: String,
    val result: ArrayList<ChatListResult>,
)

data class ChatListResult(
    val id: Long,
    val roomName: String,
    val sender: String,
    val roomId: String,
    val receiver: String,
    val message: String,
    val createdAt: String,
)
