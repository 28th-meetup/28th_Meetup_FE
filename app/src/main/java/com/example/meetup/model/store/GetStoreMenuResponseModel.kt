package com.example.meetup.model.store

data class GetStoreMenuResponseModel(

val isSuccess: Boolean,
val code: Long,
val message: String,
val result: ArrayList<GetStoreMenuResponseModelResult>,

)

data class GetStoreMenuResponseModelResult(
    val id: Long,
    val user: Long,
    val food: Long,
    val orderCount: Long,
    val totalPrice: Long,
    val orderedAt: String,
    val status: String,
)

