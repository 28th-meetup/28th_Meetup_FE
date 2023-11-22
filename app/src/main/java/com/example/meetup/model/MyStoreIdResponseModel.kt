package com.example.meetup.model

data class MyStoreIdResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: MyStoreIdResult,
)

data class MyStoreIdResult(
    val id: Long,
    val name: String,
    val description: String,
    val koreanYn: Boolean,
    val avgRate: Long,
    val minOrderAmount: Long,
    val images: List<String>,
)

