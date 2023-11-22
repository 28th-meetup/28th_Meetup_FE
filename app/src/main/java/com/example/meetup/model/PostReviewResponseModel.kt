package com.example.meetup.model

data class PostReviewResponseModel(

val isSuccess: Boolean,
val code: Long,
val message: String,
val result: PostReviewResponseModelResult,
)

data class PostReviewResponseModelResult(
    val id: Long,
    val orderId: Long,
    val rating: Long,
    val message: String,
    val image: String,
)

