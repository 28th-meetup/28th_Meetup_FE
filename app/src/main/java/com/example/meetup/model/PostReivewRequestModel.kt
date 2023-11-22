package com.example.meetup.model

data class PostReivewRequestModel(
    val orderId: Long,
    val rating: Long,
    val message: String,
)
