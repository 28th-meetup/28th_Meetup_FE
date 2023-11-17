package com.example.meetup.model

data class BasicResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: String?
)
