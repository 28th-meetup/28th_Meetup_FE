package com.example.meetup.model

data class MypageResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: String,
)
