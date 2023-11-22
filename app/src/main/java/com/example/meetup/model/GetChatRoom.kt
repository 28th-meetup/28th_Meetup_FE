package com.example.meetup.model

data class GetChatRoom(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: GetChatRoomResult,
)

data class GetChatRoomResult(
    val id: Long,
    val roomName: String,
    val roomId: String,
    val senderName: String,
    val receiverName: String,
)


