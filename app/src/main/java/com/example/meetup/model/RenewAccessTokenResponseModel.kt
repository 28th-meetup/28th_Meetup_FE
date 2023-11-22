package com.example.meetup.model

data class RenewAccessTokenResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: RenewResult,
)

data class RenewResult(
    val accessToken: String,
    val refreshToken: String,
)
