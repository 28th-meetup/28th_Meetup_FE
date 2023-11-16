package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.FragmentMypageSellerOrderManageBinding

class MypageSellerOrderManageFragment : Fragment() {

    lateinit var binding: FragmentMypageSellerOrderManageBinding
    lateinit var homeActivity: HomeActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMypageSellerOrderManageBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        binding.run {
            btnOrderCheck.setOnClickListener {
                val orderFragment = SellerOrderCheckFragment()

                val transaction = homeActivity.manager.beginTransaction()
                transaction.replace(R.id.frameArea, orderFragment)
                transaction.commit()
            }
            btnKoreanCheck.setOnClickListener{
                val certificationFragment = CertificationFragment()

                val transaction = homeActivity.manager.beginTransaction()
                transaction.replace(R.id.frameArea, certificationFragment)
                transaction.commit()
            }
        }
        return binding.root
    }
}