package com.example.meetup.fragment

import android.graphics.Color
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
import com.example.meetup.databinding.FragmentMyPageSellerBinding
import com.example.meetup.model.MyStoreIdResponseModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.sharedPreference.TokenManager
import com.example.meetup.viewmodel.MypageViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPageSellerFragment : BaseFragment<FragmentMyPageSellerBinding>(R.layout.fragment_my_page_seller) {

    lateinit var homeActivity: HomeActivity
    lateinit var viewModel: MypageViewModel

    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)


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

        homeActivity = activity as HomeActivity
        homeActivity.hideBottomNavigation(false)

        viewModel = ViewModelProvider(this)[MypageViewModel::class.java]
        viewModel.run {
            userName.observe(homeActivity) {
                Log.d("밋업", "viewModel : ${it.toString()}")
                binding.textviewMyName.text = it.toString()

                MyApplication.preferences.setString("senderNameMine",binding.textviewMyName.text.toString())
            }
        }
        viewModel.getMypageData(homeActivity)

        checkStore()

        val mypageSellerStoreManageFragment = MypageSellerStoreManageFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.framelayout_mypage_seller, mypageSellerStoreManageFragment)
            commit()
        }

        binding.textviewMyName.text = MyApplication.userName

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
            MyApplication.mypageSeller = false
            Log.d("밋업", "mypage : ${MyApplication.mypageSeller}")
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


    fun checkStore() {

        var tokenManager = TokenManager(homeActivity)

        APIS.getMyStoreId(tokenManager.getAccessToken().toString()).enqueue(object :
            Callback<MyStoreIdResponseModel> {
            override fun onResponse(
                call: Call<MyStoreIdResponseModel>,
                response: Response<MyStoreIdResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: MyStoreIdResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    binding.textviewStoreName.text = result?.result!!.name

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())

                    if (response.code() == 400) {
                        binding.textviewStoreName.text = ""
                        val dialog = StoreEnrollDialogFragment(homeActivity.manager)
                        // 알림창이 띄워져있는 동안 배경 클릭 막기
                        dialog.isCancelable = false
                        activity?.let { dialog.show(homeActivity.manager, "OrderDialog") }
                    }
                }
            }

            override fun onFailure(call: Call<MyStoreIdResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}