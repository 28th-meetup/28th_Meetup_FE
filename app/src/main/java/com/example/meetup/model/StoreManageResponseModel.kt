package com.example.meetup.model

data class StoreManageResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: StoreManageResult,
)

data class StoreManageResult(
    val processingOrderCount: Long,
    val todayOrderCount: Long,
)
