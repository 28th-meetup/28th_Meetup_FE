package com.example.meetup.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.meetup.R
import com.example.meetup.activity.CertificationActivity
import com.example.meetup.databinding.ActivityHomeBinding
import com.example.meetup.databinding.DialogSellerOrderCancelBinding
import com.example.meetup.databinding.DialogSignUpBinding

interface OrderCancelDialogInterface {
    fun onClickYesButton(id: Int)
}
class DialogOrderCancel(var manager: FragmentManager) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogSellerOrderCancelBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: OrderCancelDialogInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSellerOrderCancelBinding.inflate(inflater)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding.buttonOrderCancel.setOnClickListener {
            dismiss()
        }


        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        var activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)

        activityHomeBinding.root.setBackgroundResource(R.drawable.blur_background)
    }
}