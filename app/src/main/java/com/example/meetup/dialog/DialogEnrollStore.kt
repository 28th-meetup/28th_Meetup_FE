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
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.ActivityHomeBinding
import com.example.meetup.databinding.DialogEnrollStoreBinding
import com.example.meetup.fragment.CertificationCompleteFragment

interface EnrollStoreDialogInterface {
    fun onClickYesButton(id: Int)
}
class DialogEnrollStore(var manager: FragmentManager) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogEnrollStoreBinding? = null
    private val binding get() = _binding!!

    lateinit var homeActivity: HomeActivity

    private var confirmDialogInterface: EnrollStoreDialogInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEnrollStoreBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)


        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 클릭
        binding.buttonAddStore.setOnClickListener {
            this.confirmDialogInterface?.onClickYesButton(id!!)
            dismiss()

            val completeFragment = CertificationCompleteFragment()

            val transaction = manager.beginTransaction()
            transaction.replace(R.id.frameArea, completeFragment)
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