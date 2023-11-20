package com.example.meetup.model.chatting

data class PostChatRoomResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: PostChatRoomResponseModelResult,
)

data class PostChatRoomResponseModelResult(
    val id: Long,
    val roomName: String,
    val sender: String,
    val roomId: String,
    val receiver: String,
    val message: String,
    val createdAt: String,
)
