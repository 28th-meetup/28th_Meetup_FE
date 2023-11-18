package com.example.meetup.model.food

data class PostStoreRequestModel(
    var dto: PostStoreRequestModelDto,
    var image: String
)

data class PostStoreRequestModelDto(
    var storeId: Long,
    var categoryId: Long,
    var name: String,
    var dollarPrice: Long,
    var canadaPrice: Long,
    var description: String,
    var foodOptionRequestList: FoodOptionRequestList,
    var foodPackage: String
)

data class FoodOptionRequestList(
    var name: String,
    var dollarPrice: Long,
    var canadaPrice: Long
)


