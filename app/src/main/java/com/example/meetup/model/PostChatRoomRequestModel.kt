package com.example.meetup.model

data class PostChatRoomRequestModel(
    var messageRequestDto : MessageRequestDto
)

data class MessageRequestDto (
    var storeId : Long
)

