package com.example.meetup.model

data class GetHeartListResponseModel(

val isSuccess: Boolean,
val code: Long,
val message: String,
val result: GetHeartListResponseModelResult,
)

data class GetHeartListResponseModelResult(
    val stores: ArrayList<GetHeartListResponseModelStore>,
)

data class GetHeartListResponseModelStore(
    val id: Long,
    val name: String,
    val description: String,
    val koreanYn: Boolean,
    val avgRate: Double,
    val minOrderAmount: Long,
    val images: ArrayList<String>,
)

