package com.example.meetup.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheetFragment : BottomSheetDialogFragment() {


    override fun onStart() {
        super.onStart()

//        dialog?.window?.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT
//        )
//        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
//        dialog?.window?.setDimAmount(0.8f)
        // 배경을 흐려지게 설정
        dialog?.window?.setBackgroundDrawableResource(R.drawable.blur_background)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{

// 배경 어두운 효과 추가
        val dialog = dialog
        dialog?.window?.setDimAmount(0.5f)


        return inflater.inflate(R.layout.bottom_sheets_layout, container, false)
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}