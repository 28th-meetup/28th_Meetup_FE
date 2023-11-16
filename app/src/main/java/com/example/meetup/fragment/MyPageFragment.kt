package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentMyPageBinding

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

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


        binding.imageviewAlarm.setOnClickListener {
            val alarmFragment = AlarmFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, alarmFragment)
                addToBackStack(null)
                commit()
            }
        }
        binding.btnChangeSeller.setOnClickListener {
            val storeEnrollDialogFragment = StoreEnrollDialogFragment()

            storeEnrollDialogFragment.show(requireFragmentManager(),"StoreEnrollDialogFragment")

        }
        binding.imageviewCart.setOnClickListener {
            val cartFragment = CartFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, cartFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnOrderList.setOnClickListener {
            val orderListFragment = OrderListFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, orderListFragment)
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

        binding.btnChangeSeller.setOnClickListener {
            val myPageSellerFragment = MyPageSellerFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, myPageSellerFragment)
                commit()
            }
        }
    }


}