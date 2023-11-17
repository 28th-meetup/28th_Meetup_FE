package com.example.meetup.fragment

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.activity.AuthActivity
import com.example.meetup.databinding.FragmentSignUpAddressCheckBinding
import com.example.meetup.databinding.FragmentSignUpAddressDetailBinding
import com.example.meetup.sharedPreference.MyApplication

class SignUpAddressDetailFragment : Fragment() {

    lateinit var binding: FragmentSignUpAddressDetailBinding
    lateinit var authActivity: AuthActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpAddressDetailBinding.inflate(inflater)
        authActivity = activity as AuthActivity

        initView()

        binding.run {
            buttonCheckAddress.setOnClickListener {
                MyApplication.address.detailAddress = edittextLocation2.text.toString()

                val addressCheckFragment = SignUpAddressCheckFragment()

                val transaction = authActivity.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container_auth, addressCheckFragment)
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
                    val addressFragment = SignUpAddressFragment()

                    val transaction = authActivity.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container_auth, addressFragment)
                    transaction.commit()
                }
            }

            edittextPostNum.setText("${MyApplication.address.postalCode}")
        }
    }
}