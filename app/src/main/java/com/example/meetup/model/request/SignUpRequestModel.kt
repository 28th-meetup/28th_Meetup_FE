package com.example.meetup.model.request

data class SignUpRequestModel(
    var email : String,
    var password : String,
    var username : String,
    var countryPhoneCode : String,
    var phoneNum : String,
    var role : String,
    var imageUrl : String
)