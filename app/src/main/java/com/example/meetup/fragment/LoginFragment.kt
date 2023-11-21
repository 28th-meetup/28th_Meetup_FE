package com.example.meetup.fragment

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.meetup.R
import com.example.meetup.activity.AuthActivity
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.FragmentLoginBinding
import com.example.meetup.model.BasicResponseModel
import com.example.meetup.model.SignInResponseModel
import com.example.meetup.model.request.SignInRequestModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.sharedPreference.TokenManager
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var authActivity: AuthActivity

    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater)
        authActivity = activity as AuthActivity

        initView()

        binding.run {
            buttonLogin.setOnClickListener {
                login()
            }
        }

        return binding.root
    }

    fun initView() {
        binding.run {
            toolbar.run {
                title = "이메일로 로그인"

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_back)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    authActivity.finish()
                }
            }

            textviewEmailError.visibility = View.GONE
            textviewPasswordError.visibility = View.GONE
            buttonLogin.isEnabled = false

            edittextEmail.addTextChangedListener (object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // 텍스트 변경 전에 호출되는 메서드
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 텍스트 변경 중에 호출되는 메서드
                }

                override fun afterTextChanged(s: Editable?) {
                    checkText()
                }
            })

            edittextPassword.addTextChangedListener (object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // 텍스트 변경 전에 호출되는 메서드
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 텍스트 변경 중에 호출되는 메서드
                }

                override fun afterTextChanged(s: Editable?) {
                    checkText()
                }
            })
        }
    }

    fun checkText() {
        binding.run {
            if(edittextEmail.text.isEmpty() || edittextPassword.text.isEmpty()) {
                buttonLogin.setBackgroundResource(R.drawable.button_login_background)
                buttonLogin.isEnabled = false
            } else {
                buttonLogin.setBackgroundResource(R.drawable.button_radius)
                buttonLogin.isEnabled = true
            }

            if(!edittextEmail.text.isEmpty()) {
                edittextEmail.setBackgroundResource(R.drawable.text_login_input_background)
            } else {
                edittextEmail.setBackgroundResource(R.drawable.text_login_background)
            }

            if(!edittextPassword.text.isEmpty()) {
                edittextPassword.setBackgroundResource(R.drawable.text_login_input_background)
            } else {
                edittextPassword.setBackgroundResource(R.drawable.text_login_background)
            }
        }
    }

    fun login() {

        var SignInRequest = SignInRequestModel(binding.edittextEmail.text.toString(), binding.edittextPassword.text.toString())

        APIS.signIn(SignInRequest).enqueue(object :
            Callback<SignInResponseModel> {
            override fun onResponse(
                call: Call<SignInResponseModel>,
                response: Response<SignInResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: SignInResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    var tokenManager = TokenManager(requireContext())
                    tokenManager.saveTokens(response.body()?.result!!.accessToken, response.body()?.result!!.refreshToken)
                    Log.d("##", "access token : ${tokenManager.getAccessToken()}")
                    Log.d("##", "refresh token : ${tokenManager.getRefreshToken()}")

                    MyApplication.userName = result?.result!!.username

                    //홈 화면으로 이동 코드
                    val homeIntent = Intent(authActivity, HomeActivity::class.java)
                    startActivity(homeIntent)
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())

                    if (response.code() == 400) {
                        Snackbar.make(
                            binding.root,
                            "존재하지 않는 이메일 정보입니다.",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<SignInResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}