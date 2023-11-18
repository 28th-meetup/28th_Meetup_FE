package com.example.meetup.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentMyPageBinding
import com.example.meetup.databinding.FragmentMyPageSellerBinding


class MyPageSellerFragment : BaseFragment<FragmentMyPageSellerBinding>(R.layout.fragment_my_page_seller) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mypageSellerStoreManageFragment = MypageSellerStoreManageFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.framelayout_mypage_seller, mypageSellerStoreManageFragment)
            commit()
        }

        binding.imageviewAlarm.setOnClickListener {
            val alarmFragment = AlarmFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, alarmFragment)
                addToBackStack(null)
                commit()
            }
        }

        //가게 관리 클릭
        binding.btnStoreManage.setOnClickListener {
            btnStoreManageClick()
        }

        //주문 관리 클릭
        binding.btnOrderManage.setOnClickListener {
            btnOrderManageClick()
        }

        //구매자 모드 전환
        binding.btnChangeSeller.setOnClickListener {
            val myPageFragment = MyPageFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, myPageFragment)
                commit()
            }
        }





    }

    fun btnStoreManageClick(){
        binding.btnStoreManage.setBackgroundResource(R.drawable.store_select_border)

        binding.btnStoreManage.setTextColor(Color.parseColor("#1E1F23"))
        binding.btnOrderManage.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnOrderManage.setTextColor(Color.parseColor("#6E7178"))

        val mypageSellerStoreManageFragment = MypageSellerStoreManageFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.framelayout_mypage_seller, mypageSellerStoreManageFragment)
            addToBackStack(null)
            commit()
        }
    }

    fun btnOrderManageClick() {
        binding.btnOrderManage.setBackgroundResource(R.drawable.store_select_border)

        binding.btnOrderManage.setTextColor(Color.parseColor("#1E1F23"))
        binding.btnStoreManage.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnStoreManage.setTextColor(Color.parseColor("#6E7178"))

        val mypageSellerOrderManageFragment = MypageSellerOrderManageFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.framelayout_mypage_seller, mypageSellerOrderManageFragment)
            commit()
        }
    }


}