package com.example.meetup.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheetFragment : BottomSheetDialogFragment() {

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



}