package com.example.meetup.model

data class SearchResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: SearchResult,
)

data class SearchResult(
    val stores: List<SearchStore>,
    val foods: List<SearchFood>,
)

data class SearchStore(
    val storeDto: StoreDto,
    val isBookmarked: Boolean,
    val isFoodChangeable: Boolean,
)

data class StoreDto(
    val id: Long,
    val name: String,
    val description: String,
    val koreanYn: Boolean,
    val avgRate: Long,
    val minOrderAmount: Long,
    val images: List<String>,
)

data class SearchFood(
    val id: Long,
    val storeId: Long,
    val categoryId: Long,
    val name: String,
    val dollarPrice: Long,
    val canadaPrice: Long,
    val description: String,
    val image: String?,
)
