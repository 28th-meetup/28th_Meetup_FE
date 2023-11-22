package com.example.meetup.model.request

data class AddressesRequestModel(
    var userId : Long,
    var globalRegionId : Long,
    var address : String,
    var detailAddress : String,
    var postalCode : String,
    var fcmToken: String
)
