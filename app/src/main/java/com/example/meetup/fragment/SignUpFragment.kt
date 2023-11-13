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
import com.example.meetup.activity.AuthActivity
import com.example.meetup.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    lateinit var authActivity: AuthActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(inflater)
        authActivity = activity as AuthActivity

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
                    authActivity.finish()
                }
            }

            textviewEmailError.visibility = View.GONE
            textviewPasswordError.visibility = View.GONE
            textviewPasswordCheckError.visibility = View.GONE
            textviewNickNameError.visibility = View.GONE
        }

        var loactionSpinner = binding.spinnerLocation	// spinner
        var locationArray = resources.getStringArray(R.array.location_array)	// 배열
        setSpinner(loactionSpinner, locationArray)	// 스피너 설정
    }

    // 스피너 설정
    private fun setSpinner(spinner: Spinner, array: Array<String>) {
        var adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line
        ) { override fun getCount(): Int =  super.getCount() - 1 }  // array에서 hint 안 보이게 하기
        adapter.addAll(array.toMutableList())   // 배열 추가
        spinner.adapter = adapter               // 어댑터 달기
        spinner.setSelection(8)    // 스피너 초기값=hint
    }

    //선택시 결과를 출력해주는 함수
//    fun setupSpinnerHandler(){
//
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                textView.text = "선택됨: $position ${spinner.getItemAtPosition(position)}"
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//        }
//    }
}