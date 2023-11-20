package com.example.meetup.model

data class SellerOrderHistoryResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: OrderHistoryResult,
)

data class OrderHistoryResult(
    val orderCount: Long,
    val orderPreviewResponseList: List<OrderPreviewResponseList>,
)

data class OrderPreviewResponseList(
    val orderId: Long,
    val userName: String,
    val addressAndPostalCode: String,
    val detailAddress: String?,
    val selectedOption: String,
    val orderedAt: String,
    val orderDetailPreviewList: List<OrderDetailPreviewList>,
)

data class OrderDetailPreviewList(
    val orderDetailId: Long,
    val foodId: Long,
    val foodName: String,
    val foodOptionId: Long,
    val foodOptionName: String,
    val orderCount: Long,
)
