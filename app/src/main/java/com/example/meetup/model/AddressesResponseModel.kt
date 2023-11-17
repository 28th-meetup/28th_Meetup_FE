package com.example.meetup.model

data class AddressesResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: AddressesUser?
)

data class AddressesUser(
    var userId: Long,
    var email: String,
    var username: String,
    var globalRegion: GlobalRegionModel,
    var address: String,
    var detailAddress: String,
    var postalCode: String,
    var refreshToken: String
)

data class GlobalRegionModel(
    var countryLongName: String,
    var countryShortName: String,
    var countryKorean: String,
    var regionName: String,
    var regionKorean: String,
    var id: Long
)
