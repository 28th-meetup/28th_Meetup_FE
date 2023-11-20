package com.example.meetup.fragment

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.FragmentSellerOrderCheckBinding
import com.example.meetup.model.SellerOrderHistoryMenuResponseModel
import com.example.meetup.viewmodel.SellerOrderHistoryMenuViewModel
import com.example.meetup.viewmodel.SellerOrderHistoryViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SellerOrderCheckFragment : Fragment() {

    lateinit var binding: FragmentSellerOrderCheckBinding
    lateinit var homeActivity: HomeActivity

    val fragmentList = mutableListOf<Fragment>()

    lateinit var viewModel: SellerOrderHistoryMenuViewModel

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

        viewModel = ViewModelProvider(homeActivity)[SellerOrderHistoryMenuViewModel::class.java]

        initView()

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

    fun initView() {

        homeActivity.hideBottomNavigation(true)

        binding.run {
            toolbar.run {
                title = "주문 확인"

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_back)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
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