package com.example.meetup.model.food

data class MenuAddResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: MenuAddResponseModelResult,
)

data class MenuAddResponseModelResult(
    val id: Long,
    val storeId: Long,
    val categoryId: Long,
    val name: String,
    val dollarPrice: Double,
    val canadaPrice: Double,
    val description: String,
    val image: String,
)

