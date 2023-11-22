package com.example.meetup.model.store

data class GetStoreDetailResponseModel(

var code: Int,
var isSuccess: Boolean,
var message: String,
var result: GetStoreDetailListResult
)

data class GetStoreDetailListResult(
    var storeDto : GetStoreDetailStoreDto,
    var isBookmarked : Boolean,
    var isFoodChangeable : Boolean

)

data class GetStoreDetailStoreDto(
    var id: Long,
    var name: String,
    var description: String,
    var koreanYn: Boolean,
    var avgRate: Double,
    var minOrderAmount: Long,
    var images: ArrayList<String>
)

