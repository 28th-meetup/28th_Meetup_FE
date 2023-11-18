package com.example.meetup.model.store

data class PostStoreDtoRequestModel(
    var name: String,
    var description: String,
    var koreanYn: Boolean,
    var minOrderAmount: Long
)
