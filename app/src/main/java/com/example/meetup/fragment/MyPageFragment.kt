package com.example.meetup.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentMyPageBinding
import com.example.meetup.model.MyStoreIdResponseModel
import com.example.meetup.model.SignInResponseModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.sharedPreference.TokenManager
import com.example.meetup.viewmodel.MypageViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.fixedRateTimer

class MyPageFragment : Fragment() {

    lateinit var homeActivity: HomeActivity
    lateinit var viewModel: MypageViewModel

    lateinit var binding: FragmentMyPageBinding

    override fun onResume() {
        super.onResume()

        homeActivity.hideBottomNavigation(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeActivity = activity as HomeActivity
        binding = FragmentMyPageBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[MypageViewModel::class.java]
        viewModel.run {
            userName.observe(homeActivity) {
                Log.d("밋업", "viewModel : ${it.toString()}")
                binding.textviewMyName.text = it.toString()
            }
        }
        viewModel.getMypageData(homeActivity)

        binding.imageviewAlarm.setOnClickListener {
            val alarmFragment = AlarmFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, alarmFragment)
                addToBackStack(null)
                commit()
            }
        }
        binding.btnChangeSeller.setOnClickListener {
            val storeEnrollDialogFragment = StoreEnrollDialogFragment(homeActivity.manager)

            storeEnrollDialogFragment.show(requireFragmentManager(), "StoreEnrollDialogFragment")

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

        return binding.root
    }
}