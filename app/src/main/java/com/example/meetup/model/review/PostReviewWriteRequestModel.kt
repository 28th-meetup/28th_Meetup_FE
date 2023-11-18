package com.example.meetup.model.review

data class PostReviewWriteModel (
    var dto : PostReviewWriteModelDto,

    var imge : String
)

public data class PostReviewWriteModelDto (

    var orderId : Long,
    var rating : Long,
    var message : String
)

