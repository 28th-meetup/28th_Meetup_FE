package com.example.meetup.fragment

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.meetup.R
import com.example.meetup.activity.AuthActivity
import com.example.meetup.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    lateinit var authActivity: AuthActivity

    // 약관 동의 (false : 선택 X, true : 선택)
    var agreementAll = false
    var agreement1 = false
    var agreement2 = false
    var agreement3 = false
    var agreement4 = false
    var agreement5 = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(inflater)
        authActivity = activity as AuthActivity

        initView()

        binding.run {
            buttonCheckboxAll.setOnClickListener {
                clickAgreementAll()
            }

            buttonCheckbox1.setOnClickListener {
                agreement1 = !agreement1
                changeAgreementBackground(1)
            }

            buttonCheckbox2.setOnClickListener {
                agreement2 = !agreement2
                changeAgreementBackground(2)
            }

            buttonCheckbox3.setOnClickListener {
                agreement3 = !agreement3
                changeAgreementBackground(3)
            }

            buttonCheckbox4.setOnClickListener {
                agreement4 = !agreement4
                changeAgreementBackground(4)
            }

            buttonCheckbox5.setOnClickListener {
                agreement5 = !agreement5
                changeAgreementBackground(5)
            }

            buttonLogin.setOnClickListener {
                val addressFragment = SignUpAddressFragment()

                val transaction = authActivity.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container_auth, addressFragment)
                transaction.commit()
            }
        }

        return binding.root
    }

    fun initView() {
        binding.run {
            toolbar.run {
                title = "이메일로 회원가입"

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_close)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    // 유저 인입경로별 뒤로가기 기능 구현
                    authActivity.finish()
                }
            }

            textviewEmailError.visibility = View.GONE
            textviewPasswordError.visibility = View.GONE
            textviewPasswordCheckError.visibility = View.GONE
            textviewNickNameError.visibility = View.GONE

            buttonLogin.isEnabled = false

            textviewAgreement1Detail.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            textviewAgreement2Detail.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            textviewAgreement3Detail.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            checkaddTextChange(edittextEmail)
            checkaddTextChange(edittextPassword)
            checkaddTextChange(edittextPasswordCheck)
            checkaddTextChange(edittextNickName)
            checkaddTextChange(edittextPhoneNumber)
        }

        var phoneSpinner = binding.spinnerPhoneNumber	// spinner
        var phoneArray = resources.getStringArray(R.array.phone_array)	// 배열
        setSpinner(phoneSpinner, phoneArray)	// 스피너 설정
    }

    // 스피너 설정
    private fun setSpinner(spinner: Spinner, array: Array<String>) {
        var adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line
        ) { override fun getCount(): Int =  super.getCount() }  // array에서 hint 안 보이게 하기
        adapter.addAll(array.toMutableList())   // 배열 추가
        spinner.adapter = adapter               // 어댑터 달기
//        spinner.setSelection(, false)    // 스피너 초기값=hint
    }


    fun checkaddTextChange(view: EditText) {
        view.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트 변경 전에 호출되는 메서드
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 중에 호출되는 메서드
            }

            override fun afterTextChanged(s: Editable?) {
                if(view.text.isEmpty()) {

                } else {
                    checkInput(view)
                }
            }
        })
    }

    fun checkInput(view: View) {
        view.setBackgroundResource(R.drawable.text_login_input_background)

        binding.run {
            if(!edittextEmail.text.isEmpty() && !edittextPassword.text.isEmpty() && !edittextPasswordCheck.text.isEmpty() && !edittextNickName.text.isEmpty() && !edittextPhoneNumber.text.isEmpty()) {
                buttonLogin.run {
                    isEnabled = true
                    setBackgroundResource(R.drawable.button_radius)
                }
            }
        }
    }

    fun clickAgreementAll() {

        agreementAll = !agreementAll
        Log.d("밋업", "$agreementAll")

        if(agreementAll) {
            Log.d("밋업", "in true")
            binding.buttonCheckboxAll.setImageResource(R.drawable.checkbox_fill)
            agreement1 = true
            agreement2 = true
            agreement3 = true
            agreement4 = true
            agreement5 = true
        } else {
            binding.buttonCheckboxAll.setImageResource(R.drawable.checkbox)
            agreement1 = false
            agreement2 = false
            agreement3 = false
            agreement4 = false
            agreement5 = false
        }
        changeAgreementBackground(0)
    }

    fun changeAgreementBackground(num: Int) {
        binding.run {
            when(num) {
                0 -> {
                    if(agreement1) {
                        buttonCheckbox1.setImageResource(R.drawable.checkbox_fill)
                    } else {
                        buttonCheckbox1.setImageResource(R.drawable.checkbox)
                    }
                    if(agreement2) {
                        buttonCheckbox2.setImageResource(R.drawable.checkbox_fill)
                    } else {
                        buttonCheckbox2.setImageResource(R.drawable.checkbox)
                    }
                    if(agreement3) {
                        buttonCheckbox3.setImageResource(R.drawable.checkbox_fill)
                    } else {
                        buttonCheckbox3.setImageResource(R.drawable.checkbox)
                    }
                    if(agreement4) {
                        buttonCheckbox4.setImageResource(R.drawable.checkbox_fill)
                    } else {
                        buttonCheckbox4.setImageResource(R.drawable.checkbox)
                    }
                    if(agreement5) {
                        buttonCheckbox5.setImageResource(R.drawable.checkbox_fill)
                    } else {
                        buttonCheckbox5.setImageResource(R.drawable.checkbox)
                    }
                }

                1 -> {
                    if(agreement1) {
                        buttonCheckbox1.setImageResource(R.drawable.checkbox_fill)
                    } else {
                        buttonCheckbox1.setImageResource(R.drawable.checkbox)
                    }
                }

                2 -> {
                    if(agreement2) {
                        buttonCheckbox2.setImageResource(R.drawable.checkbox_fill)
                    } else {
                        buttonCheckbox2.setImageResource(R.drawable.checkbox)
                    }
                }

                3 -> {
                    if(agreement3) {
                        buttonCheckbox3.setImageResource(R.drawable.checkbox_fill)
                    } else {
                        buttonCheckbox3.setImageResource(R.drawable.checkbox)
                    }
                }

                4 -> {
                    if(agreement4) {
                        buttonCheckbox4.setImageResource(R.drawable.checkbox_fill)
                    } else {
                        buttonCheckbox4.setImageResource(R.drawable.checkbox)
                    }
                }

                5 -> {
                    if(agreement5) {
                        buttonCheckbox5.setImageResource(R.drawable.checkbox_fill)
                    } else {
                        buttonCheckbox5.setImageResource(R.drawable.checkbox)
                    }
                }
            }
        }
    }
}