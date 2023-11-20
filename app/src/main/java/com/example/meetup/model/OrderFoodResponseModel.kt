package com.example.meetup.model

data class OrderFoodResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: OrderFoodResult,
)

data class OrderFoodResult(
    val id: Long,
    val userId: Long,
    val storeId: Long,
    val totalCount: Long,
    val totalPrice: Long,
    val selectedOption: String,
    val orderedAt: String,
    val status: String,
    val orderFoodDetailList: List<OrderFoodDetailList>,
)

data class OrderFoodDetailList(
    val orderDetailId: Long,
    val foodId: Long,
    val foodOptionId: Long,
    val orderCount: Long,
    val orderAmount: Long,
)