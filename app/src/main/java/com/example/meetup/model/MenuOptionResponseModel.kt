package com.example.meetup.model

data class MenuOptionResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: List<MenuOptionResult>,
)

data class MenuOptionResult(
    val id: Long,
    val name: String?,
    val dollarPrice: Double,
    val canadaPrice: Double,
)
