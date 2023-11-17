package com.example.meetup.model

data class PostReviewWriteModel (
var dto : Dto,

var imge : String
)

data class Dto (

    var orderId : Long,
    var rating : Long,
    var message : String
)

