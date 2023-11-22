package com.example.meetup.model

data class GetStoreDetailReviewResponseModel(

val isSuccess: Boolean,
val code: Long,
val message: String,
val result: GetStoreDetailReviewResponseModelResult,
)

data class GetStoreDetailReviewResponseModelResult(
    val reviewList: ArrayList<ReviewList>,
)

data class ReviewList(
    val id: Long,
    val orderId: Long,
    val rating: Long,
    val message: String,
    val image: String,
)


