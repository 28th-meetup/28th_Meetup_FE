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
}