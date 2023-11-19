package com.example.meetup.model

data class FoodIdResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: FoodIdResult,
)

data class FoodIdResult(
    val id: Long,
    val storeId: Long,
    val categoryId: Long,
    val name: String,
    val dollarPrice: Long,
    val canadaPrice: Long,
    val image: String?,
    val description: String,  // 상세 설명
    val foodPackage: String,  // 배달/포장 가능
    val informationDescription: String?, // 상품 정보 이미지
    val ingredient: String?,  // 구성성분표
    val foodOptionResponseList: List<FoodOptionResponseList>,
)

data class FoodOptionResponseList(
    val id: Long,
    val name: String,
    val dollarPrice: Long,
    val canadaPrice: Long,
)
