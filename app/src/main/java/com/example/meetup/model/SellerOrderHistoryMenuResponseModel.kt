package com.example.meetup.model

data class SellerOrderHistoryMenuResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: OrderHistoryMenuResult,
)

data class OrderHistoryMenuResult(
    val orderCount: Long,
    val processingFoodList: List<ProcessingFoodList>,
)

data class ProcessingFoodList(
    val foodId: Long,
    val foodName: String,
    val totalOrderCount: Long,
    val processingFoodDetailList: List<ProcessingFoodDetailList>,
)

data class ProcessingFoodDetailList(
    val foodOptionId: Long,
    val foodOptionName: String,
    val orderCount: Long,
)

