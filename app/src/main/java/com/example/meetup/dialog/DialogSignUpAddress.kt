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
import com.example.meetup.databinding.ActivityHomeBinding
import com.example.meetup.databinding.DialogSignUpAddressBinding
import com.example.meetup.fragment.SignUpAddressCheckFragment
import com.example.meetup.fragment.SignUpAddressDetailFragment
import com.example.meetup.fragment.SignUpAddressFragment

interface SignUpAddressDialogInterface {
    fun onClickYesButton(id: Int)
}
class DialogSignUpAddress(var manager: FragmentManager, var postNum: String) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogSignUpAddressBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: SignUpAddressDialogInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSignUpAddressBinding.inflate(inflater)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding.textviewPostNum.text = postNum

        binding.buttonComplete.setOnClickListener {
            val addressFragment = SignUpAddressDetailFragment()

            val transaction = manager.beginTransaction()
            transaction.replace(R.id.container_auth, addressFragment)
            transaction.commit()

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