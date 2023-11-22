package com.example.meetup.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meetup.activity.HomeActivity
import com.example.meetup.model.BestSellingFoodList
import com.example.meetup.model.HomeResponseModel
import com.example.meetup.model.MypageResponseModel
import com.example.meetup.model.RecentSetFoodList
import com.example.meetup.model.RenewAccessTokenResponseModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageViewModel : ViewModel() {
    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    var userName = MutableLiveData<String>()

    fun checkData(context: Context) {

        var tokenManager = TokenManager(context)

        APIS.getMypageData(tokenManager.getAccessToken().toString()).enqueue(object :
            Callback<MypageResponseModel> {
            override fun onResponse(
                call: Call<MypageResponseModel>,
                response: Response<MypageResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: MypageResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    userName.value = result?.result

                    //홈 화면으로 이동 코드
                    val homeIntent = Intent(context, HomeActivity::class.java)
                    context.startActivity(homeIntent)

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        renewAccessToken(context)
                    }
                }
            }

            override fun onFailure(call: Call<MypageResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }

    fun getMypageData(context: Context) {

        var tokenManager = TokenManager(context)

        APIS.getMypageData(tokenManager.getAccessToken().toString()).enqueue(object :
            Callback<MypageResponseModel> {
            override fun onResponse(
                call: Call<MypageResponseModel>,
                response: Response<MypageResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: MypageResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    userName.value = result?.result

                    Log.d("밋업", "userName : ${userName.value}")

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        renewAccessToken(context)
                    }
                }
            }

            override fun onFailure(call: Call<MypageResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }

    fun renewAccessToken(context: Context) {

        var tokenManager = TokenManager(context)

        APIS.renewAccessToken(tokenManager.getRefreshToken().toString()).enqueue(object :
            Callback<RenewAccessTokenResponseModel> {
            override fun onResponse(
                call: Call<RenewAccessTokenResponseModel>,
                response: Response<RenewAccessTokenResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: RenewAccessTokenResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    tokenManager.saveTokens(response.body()?.result!!.accessToken, response.body()?.result!!.refreshToken)
                    Log.d("##", "access token : ${tokenManager.getAccessToken()}")
                    Log.d("##", "refresh token : ${tokenManager.getRefreshToken()}")

                    getMypageData(context)
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())
                }
            }

            override fun onFailure(call: Call<RenewAccessTokenResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}