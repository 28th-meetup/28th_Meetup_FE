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


        binding.btnChangeSeller.setOnClickListener {
            val storeEnrollDialogFragment = StoreEnrollDialogFragment()

            storeEnrollDialogFragment.show(requireFragmentManager(),"StoreEnrollDialogFragment")

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
                commit()
            }
        }
    }


}