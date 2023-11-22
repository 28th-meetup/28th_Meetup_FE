package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.FragmentOrderCompleteBinding

class OrderCompleteFragment : Fragment() {

    lateinit var binding: FragmentOrderCompleteBinding
    lateinit var homeActivity: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOrderCompleteBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        initView()

        binding.run {
            buttonHome.setOnClickListener {
                val homeFragment = HomeFragment()

                val transaction = homeActivity.manager.beginTransaction()
                transaction.replace(R.id.frameArea, homeFragment)
                transaction.commit()
            }

            buttonOrderList.setOnClickListener {
                // 마이페이지 주문내역 페이지로 이동
                val orderListFragment = OrderListFragment()

                val transaction = homeActivity.manager.beginTransaction()
                transaction.replace(R.id.frameArea, orderListFragment)
                transaction.commit()
            }
        }

        return binding.root
    }

    fun initView() {
        binding.run {
            homeActivity.hideBottomNavigation(true)
        }
    }
}