package com.example.meetup.model

data class GetChattingMessage(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: ArrayList<GetChattingMessageResult>,
)

data class GetChattingMessageResult(
    val senderName: String,
    val roomId: String,
    val message: String,
)

