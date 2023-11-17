package com.example.meetup.model

data class SignUpResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: SignUpUser?
)

data class SignUpUser(
    var id: Long,
    var email: String,
    var username: String
)