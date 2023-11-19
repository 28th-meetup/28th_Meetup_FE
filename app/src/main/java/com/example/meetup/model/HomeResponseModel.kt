package com.example.meetup.model

data class HomeResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: HomeResult?,
)

data class HomeResult(
    val globalRegionId: Long,
    val bestSellingFoodList: List<BestSellingFoodList>,
    val recentSetFoodList: List<RecentSetFoodList>,
)

data class BestSellingFoodList(
    val foodId: Long,
    val name: String,
    val storeId: Long,
    val storeName: String,
    val dollarPrice: Long,
    val canadaPrice: Long,
    val image: String?,
    val avgRate: Long,
)

data class RecentSetFoodList(
    val foodId: Long,
    val name: String,
    val storeId: Long,
    val storeName: String,
    val dollarPrice: Long,
    val canadaPrice: Long,
    val image: String?,
    val avgRate: Long,
)

