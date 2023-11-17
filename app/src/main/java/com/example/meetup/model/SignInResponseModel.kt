package com.example.meetup.model

data class SignInResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: SignInUser?
)

data class SignInUser(
    var id: Long,
    var email: String,
    var image: String,
    var role: String,
    var accessToken: String,
    var refreshToken: String,
    var accessTokenRemainTime: Long,
    var refreshTokenRemainTime: Long
)
