package com.example.meetup.fragment

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
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
import android.widget.Spinner
import com.example.meetup.R
import com.example.meetup.activity.AuthActivity
import com.example.meetup.databinding.FragmentSignUpAddressBinding
import com.example.meetup.dialog.DialogSignUpAddress
import com.example.meetup.model.AddressesValidResponseModel
import com.example.meetup.model.BasicResponseModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.MyApplication
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpAddressFragment : Fragment() {

    lateinit var binding: FragmentSignUpAddressBinding
    lateinit var authActivity: AuthActivity
    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    var isClickSpinner = false
    var regionId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpAddressBinding.inflate(inflater)
        authActivity = activity as AuthActivity

        initView()

        binding.run {
            spinnerLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when(position) {
                        0 -> {
                            isClickSpinner = false
                            checkText()
                        }
                        else -> {
                            isClickSpinner = true
                            regionId = position
                            checkText()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            edittextLocation1.addTextChangedListener (object : TextWatcher {
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

            buttonCheckPostNum.setOnClickListener {
                getPostNum(edittextLocation1.text.toString())
            }
        }

        return binding.root
    }

    fun checkText() {
        binding.run {
            if(edittextLocation1.text.isEmpty()) {
                buttonCheckPostNum.run {
                    isEnabled = false
                    setBackgroundResource(R.drawable.button_login_background)
                }
            } else {
                if(isClickSpinner) {
                    buttonCheckPostNum.run {
                        isEnabled = true
                        setBackgroundResource(R.drawable.button_black_background)
                    }
                }
                else {
                    buttonCheckPostNum.run {
                        isEnabled = false
                        setBackgroundResource(R.drawable.button_login_background)
                    }
                }
            }
        }
    }

    fun initView() {
        binding.run {
            toolbar.run {
                title = "주소 입력"
            }

            var loactionSpinner = binding.spinnerLocation	// spinner
            var locationArray = resources.getStringArray(R.array.location_array)	// 배열
            setSpinner(loactionSpinner, locationArray)	// 스피너 설정
        }
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

    fun getPostNum(address : String) {

        APIS.getPostNum(address).enqueue(object :
            Callback<AddressesValidResponseModel> {
            override fun onResponse(
                call: Call<AddressesValidResponseModel>,
                response: Response<AddressesValidResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된업 경우
                    var result: AddressesValidResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    MyApplication.address.globalRegionId = regionId.toLong()
                    MyApplication.address.address = binding.edittextLocation1.text.toString()
                    MyApplication.address.postalCode = response.body()?.result!!.postalCode

                    val dialog = DialogSignUpAddress(authActivity.supportFragmentManager, MyApplication.address.postalCode)
                    // 알림창이 띄워져있는 동안 배경 클릭 막기
                    dialog.isCancelable = false
                    authActivity?.let {
                        dialog.show(
                            it.supportFragmentManager,
                            "SignUpAddressDialog"
                        )
                    }
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())
                    Log.d("##", "onResponse 실패: " + response.body()?.message.toString())
                    Log.d("##", "onResponse 실패: " + response.errorBody())

                    if (response.code() == 400) {
                        Snackbar.make(
                            binding.root,
                            "찾을 수 없는 주소입니다. 주소를 다시 확인해주세요.",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<AddressesValidResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}