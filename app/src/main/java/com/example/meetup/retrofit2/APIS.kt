package com.example.meetup.retrofit2

import com.example.meetup.model.PostKaKaoTokenResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface APIS {

    //카카오 로그인 토큰 보내기
    @POST("auth/kakao")
    fun postKakaoToken (
        @Body token : String
    ) : Call<PostKaKaoTokenResponseModel>
    // 이메일 회원가입
    @POST("auth/signup")
    fun signUp(
        @Body parameters : SignUpRequestModel
    ) : Call<SignUpResponseModel>

    // 닉네임 중복 여부 확인
    @POST("users/nickname")
    fun checkNickName(
        @Body parameters : NickNameRequestModel
    ) : Call<BasicResponseModel>

    // 우편번호 반환
    @GET("addresses/valid")
    fun getPostNum(
        @Query("address") address: String
    ) : Call<AddressesValidResponseModel>

    // 사용자 주소 설정
    @POST("addresses")
    fun setAddresses(
        @Body parameters : AddressesRequestModel
    ) : Call<AddressesResponseModel>
}