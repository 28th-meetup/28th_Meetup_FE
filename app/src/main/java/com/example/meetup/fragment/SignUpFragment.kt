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
import com.example.meetup.dialog.DialogEnrollStore
import com.example.meetup.dialog.DialogSignUp
import com.example.meetup.model.BasicResponseModel
import com.example.meetup.model.SignUpResponseModel
import com.example.meetup.model.request.NickNameRequestModel
import com.example.meetup.model.request.SignUpRequestModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.MyApplication
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {

    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    lateinit var binding: FragmentSignUpBinding
    lateinit var authActivity: AuthActivity

    // 약관 동의 (false : 선택 X, true : 선택)
    var agreementAll = false
    var agreement1 = false
    var agreement2 = false
    var agreement3 = false
    var agreement4 = false
    var agreement5 = false

    var isAvailableNickName = false
    var isClickSpinner = false
    var phoneNumberCode = ""

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
                checkClick()
            }

            buttonCheckbox1.setOnClickListener {
                agreement1 = !agreement1
                changeAgreementBackground(1)
                checkClick()
            }

            buttonCheckbox2.setOnClickListener {
                agreement2 = !agreement2
                changeAgreementBackground(2)
                checkClick()
            }

            buttonCheckbox3.setOnClickListener {
                agreement3 = !agreement3
                changeAgreementBackground(3)
                checkClick()
            }

            buttonCheckbox4.setOnClickListener {
                agreement4 = !agreement4
                changeAgreementBackground(4)
                checkClick()
            }

            buttonCheckbox5.setOnClickListener {
                agreement5 = !agreement5
                changeAgreementBackground(5)
                checkClick()
            }

            buttonLogin.setOnClickListener {
            }

            edittextNickName.setOnEditorActionListener { v, actionId, event ->
                true
            }

            spinnerPhoneNumber.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when(position) {
                        0 -> {
                            isClickSpinner = false
                            checkClick()
                        }
                        1 -> {
                            isClickSpinner = true
                            phoneNumberCode = "KOREA"
                            checkClick()
                        }
                        2 -> {
                            isClickSpinner = true
                            phoneNumberCode = "USA"
                            checkClick()
                        }
                        3 -> {
                            isClickSpinner = true
                            phoneNumberCode = "CANADA"
                            checkClick()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
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

    fun checkClick() {
        binding.run {

            var isAvailablePassword = (edittextPassword.text.toString() == edittextPasswordCheck.text.toString())

            if(!edittextEmail.text.isEmpty() && !edittextPassword.text.isEmpty() && !edittextPasswordCheck.text.isEmpty() && !edittextNickName.text.isEmpty() && !edittextPhoneNumber.text.isEmpty()
                && agreement1 && agreement2 && agreement3 && agreement4 && isAvailablePassword && isAvailableNickName && isClickSpinner) {
                buttonLogin.run {
                    isEnabled = true
                    setBackgroundResource(R.drawable.button_radius)
                }
            } else {
                buttonLogin.run {
                    isEnabled = false
                    setBackgroundResource(R.drawable.button_login_background)
                }
            }
        }
    }

    fun checkInput(view: View) {
        view.setBackgroundResource(R.drawable.text_login_input_background)

        binding.run {
            var isAvailablePassword = (edittextPassword.text.toString() == edittextPasswordCheck.text.toString())

            if(!edittextPassword.text.isEmpty() && !edittextPasswordCheck.text.isEmpty()) {
                var passwordCheck = (edittextPassword.text.toString() == edittextPasswordCheck.text.toString())

                if(!passwordCheck) {
                    textviewPasswordCheckError.visibility = View.VISIBLE
                } else {
                    textviewPasswordCheckError.visibility = View.GONE
                }
            }

            if(view == edittextNickName) {
                isAvailableNickName = false
            }

            if(!edittextEmail.text.isEmpty() && !edittextPassword.text.isEmpty() && !edittextPasswordCheck.text.isEmpty() && !edittextNickName.text.isEmpty() && !edittextPhoneNumber.text.isEmpty()
                && agreement1 && agreement2 && agreement3 && agreement4 && isAvailablePassword && isAvailableNickName && isClickSpinner) {
                buttonLogin.run {
                    isEnabled = true
                    setBackgroundResource(R.drawable.button_radius)
                }
            } else {
                buttonLogin.run {
                    isEnabled = false
                    setBackgroundResource(R.drawable.button_login_background)
                }
            }
        }
    }

    fun clickAgreementAll() {

        agreementAll = !agreementAll

        if(agreementAll) {
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