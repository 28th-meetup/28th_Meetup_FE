package com.example.meetup.model

data class CategoryIdResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: List<Food>?,
)

data class Food(
    val foodId: Long,
    val name: String,
    val storeId: Long,
    val storeName: String,
    val dollarPrice: Double,
    val canadaPrice: Double,
    val image: String?,
    val avgRate: Double,
)
