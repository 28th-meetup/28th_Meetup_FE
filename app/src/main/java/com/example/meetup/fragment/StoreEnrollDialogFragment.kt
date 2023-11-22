package com.example.meetup.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.ActivityHomeBinding
import com.example.meetup.databinding.DialogEnrollStoreBinding
import com.example.meetup.databinding.FragmentStoreEnrollDialogBinding
import com.example.meetup.dialog.EnrollStoreDialogInterface
import com.google.android.material.card.MaterialCardView


class StoreEnrollDialogFragment(var manager: FragmentManager) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: FragmentStoreEnrollDialogBinding? = null
    private val binding get() = _binding!!

    lateinit var homeActivity: HomeActivity

    private var confirmDialogInterface: EnrollStoreDialogInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreEnrollDialogBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)


        binding.btnBack.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 클릭
        binding.btnEnroll.setOnClickListener {
            this.confirmDialogInterface?.onClickYesButton(id!!)
            dismiss()

            val storeEnrollFragment = StoreEditFragment()

            val transaction = manager.beginTransaction()
            transaction.replace(R.id.frameArea, storeEnrollFragment)
            transaction.addToBackStack("")
            transaction.commit()
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