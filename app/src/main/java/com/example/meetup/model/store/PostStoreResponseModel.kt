package com.example.meetup.model.store

data class PostStoreResponseModel(
    var code: Int,
    var isSuccess: Boolean,
    var message: String,
    var result: PostStoreResult
)

data class PostStoreResult(
    var id: Long,
    var name: String,
    var description: String,
    var koreanYn: Boolean,
    var avgRate: Long,
    var minOrderAmount: Long,
    var images: ArrayList<String>
)

