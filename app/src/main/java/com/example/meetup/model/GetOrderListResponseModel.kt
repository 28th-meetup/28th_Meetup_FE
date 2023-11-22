package com.example.meetup.model

data class GetOrderListResponseModel(

val isSuccess: Boolean,
val code: Long,
val message: String,
val result: ArrayList<GetOrderListResponseModelResult>,
)

data class GetOrderListResponseModelResult(
    val id: Long,
    val userId: Long,
    val storeId: Long,
    val storeName: String,
    val storeImage: String,
    val storeImage2: String,
    val storeImage3: String,
    val totalCount: Long,
    val totalPrice: Long,
    val selectedOption: String,
    val orderedAt: String,
    val status: String,
    val isReviewed: Boolean,
    val orderFoodDetailList: ArrayList<GetOrderListResponseModelOrderFoodDetailList>,
)

data class GetOrderListResponseModelOrderFoodDetailList(
    val orderDetailId: Long,
    val foodId: Long,
    val foodName: String,
    val foodOptionId: Long,
    val foodOptionName: String,
    val orderCount: Long,
    val orderAmount: Long,
)
