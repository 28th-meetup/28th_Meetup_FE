package com.example.meetup.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.FragmentMypageSellerOrderManageBinding
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.MypageViewModel

class MypageSellerOrderManageFragment : Fragment() {

    lateinit var binding: FragmentMypageSellerOrderManageBinding
    lateinit var homeActivity: HomeActivity

    lateinit var viewModel: MypageViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMypageSellerOrderManageBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        viewModel = ViewModelProvider(this)[MypageViewModel::class.java]
        viewModel.run {
            storeProcessingOrderCount.observe(homeActivity) {
                binding.textviewProcessOrder.text = it.toString() + "건"
            }
            storeTodayOrderCount.observe(homeActivity) {
                binding.textviewOrderToday.text = it.toString() + "건"
            }
        }
        viewModel.getStoreManageData(homeActivity)

        binding.run {
            btnOrderCheck.setOnClickListener {
                val orderFragment = SellerOrderCheckFragment()

                val transaction = homeActivity.manager.beginTransaction()
                transaction.replace(R.id.frameArea, orderFragment)
                transaction.addToBackStack("")
                transaction.commit()

            }

            btnKoreanCheck.setOnClickListener{
                val certificationFragment = CertificationFragment()

                val transaction = homeActivity.manager.beginTransaction()
                transaction.replace(R.id.frameArea, certificationFragment)
                transaction.addToBackStack("")
                transaction.commit()
            }
        }
        return binding.root
    }
}