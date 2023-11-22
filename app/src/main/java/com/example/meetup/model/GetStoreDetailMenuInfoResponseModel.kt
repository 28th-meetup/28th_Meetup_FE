package com.example.meetup.model

data class GetStoreDetailMenuInfoResponseModel (
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: GetStoreDetailMenuInfoResponseModelResult,
)

data class GetStoreDetailMenuInfoResponseModelResult(
    val storeInfoDto: StoreInfoDto,
    val isBookmarked: Boolean,
    val isFoodChangeable: Boolean,
)

data class StoreInfoDto(
    val id: Long,
    val name: String,
    val description: String,
    val koreanYn: Boolean,
    val avgRate: Double,
    val minOrderAmount: Long,
    val images: List<Any?>,
    val address: String,
    val detailAddress: String,
    val phoneNum: String,
    val deliveryRegion: String,
    val operationTime: String,

)