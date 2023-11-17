package com.example.meetup.model

data class AddressesValidResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: PostNum?
)

data class PostNum(
    val formattedAddress: String,
    val postalCode: String
)
