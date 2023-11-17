package com.example.meetup.retrofit2

import androidx.core.os.BuildCompat.PrereleaseSdkCheck
import com.example.meetup.model.AddressesResponseModel
import com.example.meetup.model.AddressesValidResponseModel
import com.example.meetup.model.BasicResponseModel
import com.example.meetup.model.PostKaKaoTokenResponseModel
import com.example.meetup.model.PostReviewWriteResponseModel
import com.example.meetup.model.SignInResponseModel
import com.example.meetup.model.SignUpResponseModel
import com.example.meetup.model.request.AddressesRequestModel
import com.example.meetup.model.request.AddressesValidRequestModel
import com.example.meetup.model.request.NickNameRequestModel
import com.example.meetup.model.request.SignInRequestModel
import com.example.meetup.model.request.SignUpRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface APIS {

    //카카오 로그인 토큰 보내기
    @POST("auth/kakao")
    fun postKakaoToken (
        @Body token : String
    ) : Call<PostKaKaoTokenResponseModel>

    // 일반(이메일) 로그인
    @POST("auth/signin")
    fun signIn(
        @Body parameters : SignInRequestModel
    ) : Call<SignInResponseModel>

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

    //리뷰 작성
    @POST("review")
    fun postReviewWrite(
        @Header ("token") token: String
    ) : Call<PostReviewWriteResponseModel>
}