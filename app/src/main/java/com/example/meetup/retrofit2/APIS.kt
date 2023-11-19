package com.example.meetup.retrofit2

import com.example.meetup.model.AddressesResponseModel
import com.example.meetup.model.AddressesValidResponseModel
import com.example.meetup.model.BasicResponseModel
import com.example.meetup.model.CategoryIdResponseModel
import com.example.meetup.model.GetHeartListResponseModel
import com.example.meetup.model.FoodIdResponseModel
import com.example.meetup.model.FoodOptionResponseList
import com.example.meetup.model.HomeResponseModel
import com.example.meetup.model.MenuAddRequestModelDto
import com.example.meetup.model.MenuOptionResponseModel
import com.example.meetup.model.PostKaKaoTokenResponseModel
import com.example.meetup.model.PostReviewWriteResponseModel
import com.example.meetup.model.PostStoreResponseModel
import com.example.meetup.model.store.PostStoreDtoRequestModel
import com.example.meetup.model.SignInResponseModel
import com.example.meetup.model.SignUpResponseModel
import com.example.meetup.model.chatting.ChatListResponseModel
import com.example.meetup.model.chatting.ChatListResult
import com.example.meetup.model.food.MenuAddResponseModel
import com.example.meetup.model.request.AddressesRequestModel
import com.example.meetup.model.request.NickNameRequestModel
import com.example.meetup.model.request.SignInRequestModel
import com.example.meetup.model.request.SignUpRequestModel
import com.example.meetup.model.store.GetStoreDetailListResult
import com.example.meetup.model.store.GetStoreDetailResponseModel
import com.example.meetup.model.store.GetStoreListResponseModel
import com.example.meetup.model.store.GetStoreListStores

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Part
import retrofit2.http.Query

interface APIS {

    //카카오 로그인 토큰 보내기
    @POST("auth/kakao")
    fun postKakaoToken(
        @Body token: String
    ): Call<PostKaKaoTokenResponseModel>

    // 일반(이메일) 로그인
    @POST("auth/signin")
    fun signIn(
        @Body parameters: SignInRequestModel
    ): Call<SignInResponseModel>

    // 이메일 회원가입
    @POST("auth/signup")
    fun signUp(
        @Body parameters: SignUpRequestModel
    ): Call<SignUpResponseModel>

    // 닉네임 중복 여부 확인
    @POST("users/nickname")
    fun checkNickName(
        @Body parameters: NickNameRequestModel
    ): Call<BasicResponseModel>

    // 우편번호 반환
    @GET("addresses/valid")
    fun getPostNum(
        @Query("address") address: String
    ): Call<AddressesValidResponseModel>

    // 사용자 주소 설정
    @POST("addresses")
    fun setAddresses(
        @Body parameters : AddressesRequestModel
    ) : Call<AddressesResponseModel>

    // 홈 메뉴 조회하기
    @GET("food/home")
    fun getHomeFood(
        @Header("Authorization") Authorization: String
    ) : Call<HomeResponseModel>

    // 특정 카테고리 메뉴 조회하기
    @GET("food/category/{categoryId}")
    fun getCategoryFood(
        @Header("Authorization") Authorization: String,
        @Path("categoryId") categoryId: Int
    ) : Call<CategoryIdResponseModel>

    // 메뉴 1개 상세 조회
    @GET("food/{foodId}")
    fun getFoodMenu(
        @Header("Authorization") Authorization: String,
        @Path("foodId") foodId: Int
    ) : Call<FoodIdResponseModel>

    // 메뉴 1개 옵션 조회
    @GET("food/{foodId}/option")
    fun getFoodMenuOption(
        @Header("Authorization") Authorization: String,
        @Path("foodId") foodId: Int
    ) : Call<MenuOptionResponseModel>

    //리뷰 작성
    @POST("review")
    fun postReviewWrite(
        @Header("Authorization") Authorization: String
    ): Call<PostReviewWriteResponseModel>

    //가게 리스트 검색
    @GET("store")
    fun getStoreList(
        @Header("Authorization") Authorization: String,
        @Query("keyword") keyword : String,
        @Query("field") field : String,
        @Query("direction") direction : String,

    ) : Call<GetStoreListResponseModel>

    //가게 상세보기
    @GET("store/{storeId}")
    fun getStoreDetail(
        @Header("Authorization") Authorization: String,
        @Path("storeId") storeId: Long
    ) : Call<GetStoreDetailResponseModel>

    //가게 등록하기
    @Multipart
    @POST("store")
    fun postStore(
        @Header("Authorization") Authorization: String,
        @Part("dto") dto: PostStoreDtoRequestModel,
        @Part image: List<MultipartBody.Part>
    ) : Call<PostStoreResponseModel>


    //찜하기
    @POST("store/bookmark/{storeId}")
    fun postHeart(
        @Header("Authorization") Authorization: String,
        @Path("storeId") storeId : Int
        ) :  Call<GetStoreDetailResponseModel>

    //찜한 가게 가져오기
    @GET("store/bookmark")
    fun getHeartList(
        @Header("Authorization") Authorization: String,

        ) : Call<GetHeartListResponseModel>
    //채팅 리스트
    @GET("chat/rooms")
    fun getChatList(
        @Header("Authorization") Authorization: String,

        ) : Call<ChatListResponseModel>

    //메뉴 등록
    @Multipart
    @POST("food")
    fun postMenu(
        @Header("Authorization") Authorization: String,
        @Part("dto") dto : MenuAddRequestModelDto,
        @Part image : MultipartBody.Part,
        @Part informationImage : MultipartBody.Part
        ) : Call<MenuAddResponseModel>
}