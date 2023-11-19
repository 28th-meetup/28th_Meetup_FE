package com.example.meetup.model

data class PostStoreResponseModel(

val isSuccess: Boolean,
val code: Long,
val message: String,
val result: PostStoreResponseModelResult
)

data class PostStoreResponseModelResult(
    val id: Long,
    val name: String,
    val description: String,
)

