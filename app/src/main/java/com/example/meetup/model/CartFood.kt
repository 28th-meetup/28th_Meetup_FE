package com.example.meetup.model

data class CartFood(
    var storeId: Long,
    var storeName: String,
    var categoryId: Long,
    var foodImage: String?,
    var foodName: String,
    var foodId: Long,
    var foodOptionName: String,
    var foodOptionId: Long,
    var orderDeliveryOption: String,
    var orderCount: Long,
    var orderEachPrice: Double,
    var isChecked: Boolean
)