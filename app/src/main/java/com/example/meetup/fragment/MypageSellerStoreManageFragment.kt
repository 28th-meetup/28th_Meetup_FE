package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentMypageSellerStoreManageBinding


class MypageSellerStoreManageFragment : BaseFragment<FragmentMypageSellerStoreManageBinding>(R.layout.fragment_mypage_seller_store_manage) {


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


        //메뉴 등록 및 편집 클릭
        binding.btnMenuSetting.setOnClickListener {
            val menuEditFragment = MenuEditFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, menuEditFragment)
                addToBackStack(null)
                commit()
            }
        }

        //가게 등록 클릭
        binding.btnStoreEnroll.setOnClickListener {
            val storeEditFragment = StoreEditFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, storeEditFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnChattingList.setOnClickListener {
            val chattingFragment = ChattingFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, chattingFragment)
                addToBackStack(null)
                commit()
            }
        }
    }


}