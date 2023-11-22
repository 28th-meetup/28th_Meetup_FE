package com.example.meetup.model

data class MenuAddRequestModel(
val dto: MenuAddRequestModelDto,
val image: String,
val informationImage: String,
)

data class MenuAddRequestModelDto(
    val categoryId: Long,
    val name: String,
    val dollarPrice: Double,
    val canadaPrice: Double,
    val description: String,
    val foodOptionRequestList: ArrayList<MenuAddRequestModelDtoFoodOptionRequestList>,
    val foodPackage: String,
    val ingredient: String,
)

data class MenuAddRequestModelDtoFoodOptionRequestList(
    val name: String,
    val dollarPrice: Double,
    val canadaPrice: Double,
)

