package com.example.meetup.fragment

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.meetup.R
import com.example.meetup.activity.AuthActivity
import com.example.meetup.activity.MainActivity
import com.example.meetup.databinding.FragmentSignUpAddressCheckBinding
import com.example.meetup.dialog.DialogSignUpAddress
import com.example.meetup.model.AddressesResponseModel
import com.example.meetup.model.AddressesValidResponseModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.MyApplication
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpAddressCheckFragment : Fragment() {

    lateinit var binding: FragmentSignUpAddressCheckBinding
    lateinit var authActivity: AuthActivity

    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpAddressCheckBinding.inflate(inflater)
        authActivity = activity as AuthActivity

        initView()

        binding.run {
            buttonSignUp.setOnClickListener {
                // 사용자 주소 설정 API
                setAddresses()
            }

            buttonBack.setOnClickListener {
                val addressFragment = SignUpAddressDetailFragment()

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
                title = "주소 입력"

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_back)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    val addressFragment = SignUpAddressDetailFragment()

                    val transaction = authActivity.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container_auth, addressFragment)
                    transaction.commit()
                }
            }

            var region = resources.getStringArray(R.array.location_array).get(MyApplication.address.globalRegionId.toInt())
            textviewLocation1Value.text = "${region}"
            textviewLocation2Value.text = "${MyApplication.address.address}"
            textviewLocation3Value.text = "${MyApplication.address.detailAddress}"
            textviewLocation4Value.text = "${MyApplication.address.postalCode}"
        }
    }

    fun setAddresses() {

        Log.d("밋업", "사용자 주소 설정 : ${MyApplication.address}")

        APIS.setAddresses(MyApplication.address).enqueue(object :
            Callback<AddressesResponseModel> {
            override fun onResponse(
                call: Call<AddressesResponseModel>,
                response: Response<AddressesResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된업 경우
                    var result: AddressesResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    authActivity.finish()

                    val mainIntent = Intent(authActivity,MainActivity::class.java)
                    startActivity(mainIntent)

                    Toast.makeText(requireContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show()
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())
                    Log.d("##", "onResponse 실패: " + response.body()?.message.toString())
                    Log.d("##", "onResponse 실패: " + response.errorBody())

                    if (response.code() == 400) {
                        Snackbar.make(
                            binding.root,
                            "존재하지 않는 정보입니다",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<AddressesResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}