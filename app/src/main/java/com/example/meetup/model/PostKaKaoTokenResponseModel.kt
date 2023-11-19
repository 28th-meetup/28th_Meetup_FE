package com.example.meetup.model

data class PostKaKaoTokenResponseModel(

    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: Result
)

data class Result(
    val dto: Info,
    val isSignUp: Boolean,
)

data class Info(

    var id: Long,
    var email: String,
    var image: String,
    var role: String,
    var accessToken: String,
    var refreshToken: String,
    var accessTokenRemainTime: Long,
    var refreshTokenRemainTime: Long

)