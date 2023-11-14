package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.FragmentSellerOrderCheckBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SellerOrderCheckFragment : Fragment() {

    lateinit var binding: FragmentSellerOrderCheckBinding
    lateinit var homeActivity: HomeActivity

    val fragmentList = mutableListOf<Fragment>()

    // 탭에 표시할 이름
    val tabName = arrayOf(
        "진행중 주문","메뉴별 주문", "완료 주문"
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSellerOrderCheckBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        fragmentList.clear()
        fragmentList.add(SellerOrderInProgressFragment())
        fragmentList.add(SellerOrderMenuFragment())
        fragmentList.add(SellerOrderCompleteFragment())

        binding.run {
            pager.setUserInputEnabled(false)
            pager.adapter = TabAdapterClass(homeActivity)

            // 탭 구성
            val tabLayoutMediator = TabLayoutMediator(tab, pager){ tab: TabLayout.Tab, i: Int ->
                tab.text = tabName[i]
            }
            tabLayoutMediator.attach()

        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        fragmentList.clear()
        fragmentList.add(SellerOrderInProgressFragment())
        fragmentList.add(SellerOrderMenuFragment())
        fragmentList.add(SellerOrderCompleteFragment())
        binding.pager.requestLayout()
    }

    // adapter 클래스
    inner class TabAdapterClass(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

    }
}