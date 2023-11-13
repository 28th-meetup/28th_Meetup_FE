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

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var authActivity: AuthActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater)
        authActivity = activity as AuthActivity

        initView()

        binding.run {
            buttonLogin.setOnClickListener {
                //홈 화면으로 이동 코드
                val homeIntent = Intent(authActivity, HomeActivity::class.java)
                startActivity(homeIntent)
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
                    if(edittextEmail.text.isEmpty() || edittextPassword.text.isEmpty()) {
                        buttonLogin.setBackgroundResource(R.drawable.button_login_background)
                        buttonLogin.isEnabled = false
                    } else {
                        buttonLogin.setBackgroundResource(R.drawable.button_radius)
                        buttonLogin.isEnabled = true
                    }

                    if(!edittextPassword.text.isEmpty()) {
                        edittextPassword.setBackgroundResource(R.drawable.text_login_input_background)
                    } else {
                        edittextPassword.setBackgroundResource(R.drawable.text_login_background)
                    }
                }
            })
        }
    }
}