package com.example.meetup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.meetup.R
import com.example.meetup.databinding.BottomSheetsLayoutBinding
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.CategoryFoodViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ModalBottomSheetFragment(var activity: ViewModelStoreOwner) : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetsLayoutBinding
    lateinit var categoryViewModel: CategoryFoodViewModel

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
        categoryViewModel = ViewModelProvider(activity)[CategoryFoodViewModel::class.java]

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
                categoryViewModel.getCategoryFoodInfo(requireContext(), "REVIEW_HIGH", MyApplication.categoryId)
                MyApplication.filtering = "후기 많은 순"
                dismiss()
            }
            textviewTopRate.setOnClickListener {
                categoryViewModel.getCategoryFoodInfo(requireContext(), "RATING_HIGH", MyApplication.categoryId)
                MyApplication.filtering = "평점 많은 순"
                dismiss()
            }
            textviewHighPrice.setOnClickListener {
                categoryViewModel.getCategoryFoodInfo(requireContext(), "PRICE_HIGH", MyApplication.categoryId)
                MyApplication.filtering = "높은 가격 순"
                dismiss()
            }
            textviewLowPrice.setOnClickListener {
                categoryViewModel.getCategoryFoodInfo(requireContext(), "PRICE_LOW", MyApplication.categoryId)
                MyApplication.filtering = "낮은 가격 순"
                dismiss()
            }
            textviewRecent.setOnClickListener {
                categoryViewModel.getCategoryFoodInfo(requireContext(), "RECENTLY_ADDED", MyApplication.categoryId)
                MyApplication.filtering = "최신순"
                dismiss()
            }
        }
    }


}