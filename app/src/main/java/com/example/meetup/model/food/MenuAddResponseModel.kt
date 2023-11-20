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
    val dollarPrice: Long,
    val canadaPrice: Long,
    val description: String,
    val image: String,
)

