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
import com.example.meetup.databinding.FragmentSignUpAddressCheckBinding

class SignUpAddressCheckFragment : Fragment() {

    lateinit var binding: FragmentSignUpAddressCheckBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpAddressCheckBinding.inflate(inflater)

        initView()

        return binding.root
    }

    fun initView() {
        binding.run {
            toolbar.run {
                title = "주소 입력"

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

                }
            }
        }
    }
}