package com.example.meetup.model.store

data class GetStoreMenuResponseModel(

val isSuccess: Boolean,
val code: Long,
val message: String,
val result: ArrayList<GetStoreMenuResponseModelResult>,
)

data class GetStoreMenuResponseModelResult(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val dollarPrice: Long,
    val canadaPrice: Long,
    val description: String,
    val recommendCount: Long,
    val image: String,
)
