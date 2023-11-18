package com.example.meetup.model.store

data class PostStoreDtoRequestModel(
    val name: String,
    val minOrderAmount: Long,
    val description: String,
    val countryPhoneCode: String,
    val phoneNum: String,
    val globalRegionId: Long,
    val address: String,
    val detailAddress: String,
    val deliveryRegion: String,
    val operationTime: String,
    val foodChangeYn: Boolean,
    val koreanYn: Boolean,
)
