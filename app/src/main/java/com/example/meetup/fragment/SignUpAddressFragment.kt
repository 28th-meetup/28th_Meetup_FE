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
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.meetup.R
import com.example.meetup.databinding.FragmentSignUpAddressBinding
import com.example.meetup.databinding.FragmentSignUpAddressDetailBinding

class SignUpAddressFragment : Fragment() {

    lateinit var binding: FragmentSignUpAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpAddressBinding.inflate(inflater)

        initView()

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

                }
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
}