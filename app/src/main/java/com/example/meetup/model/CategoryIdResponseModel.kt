package com.example.meetup.model

data class CategoryIdResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: List<Food>?,
)

data class Food(
    val id: Long,
    val storeId: Long,
    val categoryId: Long,
    val name: String,
    val dollarPrice: Long,
    val canadaPrice: Long,
    val description: String,
    val image: String?,
)
