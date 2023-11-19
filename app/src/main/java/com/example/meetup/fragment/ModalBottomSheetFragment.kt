package com.example.meetup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.meetup.R
import com.example.meetup.databinding.BottomSheetsLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ModalBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetsLayoutBinding
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

        binding = BottomSheetsLayoutBinding.inflate(inflater)

// 배경 어두운 효과 추가
        val dialog = dialog
        dialog?.window?.setDimAmount(0.5f)


        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            textviewTopReview.setOnClickListener {
                dismiss()
            }
            textviewTopRate.setOnClickListener {
                dismiss()
            }
            textviewHighPrice.setOnClickListener {
                dismiss()
            }
            textviewLowPrice.setOnClickListener {
                dismiss()
            }
            textviewRecent.setOnClickListener {
                dismiss()
            }
        }
    }


}