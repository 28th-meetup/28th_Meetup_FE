package com.example.meetup.model.store

data class GetStoreListResponseModel(
    var code: Int,
    var isSuccess: Boolean,
    var message: String,
    var result: GetStoreResult
)


data class GetStoreResult(
    var stores : ArrayList<GetStoreListStores>,
    var foods : ArrayList<GetStoreListFoods>
)

data class GetStoreListStores(

    var storeDto : GetStoreListStoreDto,
    var isBookmarked : Boolean,
    var isFoodChangeable: Boolean


)
data class GetStoreListFoods(
    var id: Long,
    var storeId: Long,
    var categoryId: Long,
    var name: String,
    var dollarPrice: Long,
    var canadaPrice: Long,
    var description: String,
    var image: String
)

data class GetStoreListStoreDto(
    var id: Long,
    var name: String,
    var description: String,
    var koreanYn: Boolean,
    var avgRate: Double,
    var minOrderAmount: Long,
    var images: ArrayList<String>
)

