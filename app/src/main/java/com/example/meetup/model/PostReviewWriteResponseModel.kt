package com.example.meetup.model

data class PostReviewWriteResponseModel(
    var isSuccess: Boolean,
    var code: Long,
    var message: String,
    var result: PostReviewWriteResult
)

data class PostReviewWriteResult(
    var id: Long,
    var orderId: Long,
    var rating: Long,
    var message: String,
    var image: String

)
