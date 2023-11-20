package com.example.meetup.model.request

data class OrderFoodRequestModel(
    val store: Long,
    val totalPrice: Long,
    val totalCount: Long,
    val selectedOption: String,
    val orderFoodDetailList: List<OrderFoodDetailList>,
)

data class OrderFoodDetailList(
    val foodId: Long,
    val foodOptionId: Long,
    val orderCount: Long,
    val orderAmount: Long,
)
