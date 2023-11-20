package com.example.meetup.model

data class MenuListResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: MenuListResponseModelResult,
)

data class MenuListResponseModelResult(
    val id: Long,
    val name: String,
    val description: String,
    val koreanYn: Boolean,
    val avgRate: Double,
    val minOrderAmount: Long,
    val images: List<Any?>,
)

