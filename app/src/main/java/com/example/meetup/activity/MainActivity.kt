package com.example.meetup.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.meetup.databinding.ActivityMainBinding
import com.example.meetup.model.PostKaKaoTokenResponseModel
import com.example.meetup.model.RenewAccessTokenResponseModel
import com.example.meetup.model.SignInResponseModel
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.sharedPreference.PreferenceUtil
import com.example.meetup.sharedPreference.TokenManager
import com.example.meetup.viewmodel.HomeFoodViewModel
import com.example.meetup.viewmodel.MypageViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val APIS = RetrofitInstance.retrofitInstance().create(APIS::class.java)
    lateinit var binding: ActivityMainBinding

    lateinit var authActivity: AuthActivity

    lateinit var sharedPreferenceManager: PreferenceUtil

    lateinit var viewModel: MypageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        authActivity = AuthActivity()

        viewModel = ViewModelProvider(this)[MypageViewModel::class.java]

        setFCMToken()
        viewModel.checkData(this)

        binding.run {
            // 이메일 로그인 버튼 클릭
            buttonEmailLogin.setOnClickListener {
                val loginIntent = Intent(this@MainActivity,AuthActivity::class.java)
                loginIntent.putExtra("auth",1)
                startActivity(loginIntent)
            }
            // 카카오 로그인 버튼 클릭
            buttonKakaoLogin.setOnClickListener {
                startKakaoLogin(this@MainActivity)
            }
            // 이메일 회원가입 버튼 클릭
            buttonSignUp.setOnClickListener {
                val signUpIntent = Intent(this@MainActivity,AuthActivity::class.java)
                signUpIntent.putExtra("auth",0)
                startActivity(signUpIntent)
            }
        }


        setContentView(binding.root)
    }

    fun startKakaoLogin(context: Context) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "카카오계정으로 로그인 실패 1", error)
            } else if (token != null) {
                Log.i(ContentValues.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")

                getKakaoToken(token.accessToken)


            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(ContentValues.TAG, "카카오톡으로 로그인 실패 2", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {

                    Log.i(ContentValues.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")


                    Log.d("getKakaoToken", "${token.accessToken}")
                    getKakaoToken(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun getKakaoToken(kakaoToken: String) {
        APIS.postKakaoToken(kakaoToken)
            .enqueue(object : Callback<PostKaKaoTokenResponseModel> {

                override fun onResponse(
                    call: Call<PostKaKaoTokenResponseModel>,
                    response: Response<PostKaKaoTokenResponseModel>
                ) {
                    if (response.isSuccessful) {


                        Log.d("PostKaKaoTokenResponseModel", response.body().toString())

                        if(response.body()!!.result?.isSignUp == true) {

                            MyApplication.address.userId = response.body()!!.result?.dto!!.id

                            val authIntent = Intent(this@MainActivity,AuthActivity::class.java)
                            authIntent.putExtra("auth", 2)
                            startActivity(authIntent)
                        }
                        else {
                            var tokenManager = TokenManager(this@MainActivity)
                            tokenManager.saveTokens(response.body()!!.result?.dto!!.accessToken, response.body()!!.result?.dto!!.refreshToken)
                            Log.d("밋업", "access token : ${tokenManager.getAccessToken()}")
                            Log.d("밋업", "access token : ${tokenManager.getRefreshToken()}")

                            //홈 화면으로 이동 코드
                            val homeIntent = Intent(this@MainActivity,HomeActivity::class.java)
                            startActivity(homeIntent)
                        }

                    } else {
                        Log.d("PostKaKaoTokenResponseModel", "fail 1")
                    }
                }

                override fun onFailure(call: Call<PostKaKaoTokenResponseModel>, t: Throwable) {
                    Log.d("PostKaKaoTokenResponseModel", "fail 2")
                }
            })
    }

    fun setFCMToken(){

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("FCM Token", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d("FCM Token", "$token")
            MyApplication.preferences.setFCMToken(token)
            Log.d("토큰", "FCM 토큰 : ${MyApplication.preferences.getFCMToken()}")

            if (this::sharedPreferenceManager.isInitialized) {
                Log.d("FCM Token", "this::sharedPreferenceManager.isInitialized")
                sharedPreferenceManager.setFCMToken(token)
            }
        }
    }

}