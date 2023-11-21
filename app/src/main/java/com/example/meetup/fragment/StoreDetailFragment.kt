package com.example.meetup.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.activity.ChattingActivity
import com.example.meetup.databinding.FragmentStoreBinding
import com.example.meetup.databinding.FragmentStoreDetailBinding
import com.example.meetup.model.MessageRequestDto
import com.example.meetup.model.chatting.PostChatRoomResponseModel
import com.example.meetup.model.store.GetStoreDetailResponseModel
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.ChattingRoomViewModel
import com.example.meetup.viewmodel.StoreListViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreDetailFragment : Fragment() {
    private var _binding: FragmentStoreDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StoreListViewModel
    private lateinit var chattingRoomViewModel: ChattingRoomViewModel
    private lateinit var API: APIS

    var isHeartCheck = false

    var storeId = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoreDetailBinding.inflate(inflater, container, false)
        val view = binding.root


        storeId = MyApplication.preferences.getString("storeId", "").toLong()
        Log.d("storeID", storeId.toString())

        viewModel = ViewModelProvider(requireActivity()).get(StoreListViewModel::class.java)

        chattingRoomViewModel = ViewModelProvider(requireActivity()).get(ChattingRoomViewModel::class.java)
        viewModel.getStoreDetail(requireContext(), storeId)

        viewModel.storeDetail.observe(viewLifecycleOwner) {

            Glide.with(this)
                .load(it.result.storeDto.images[0])
                .into(binding.imageView)

            binding.textviewStoreName.text = it.result.storeDto.name

            binding.textviewMinOrderPrice.text = it.result.storeDto.minOrderAmount.toString()

            binding.textviewRate.text = it.result.storeDto.avgRate.toString()

            if (it.result.isBookmarked == true) {
                binding.imageviewHeart.setImageResource(R.drawable.ic_heart_fill)
                isHeartCheck = true
            } else {
                binding.imageviewHeart.setImageResource(R.drawable.ic_heart)
                isHeartCheck = false
            }

        }
        //초기 설정
        val storeDetailMenuFragment = StoreDetailMenuFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.store_detail_frame_area1, storeDetailMenuFragment)
            addToBackStack(null)
            commit()
        }



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //메뉴 클릭
        binding.btnMenu.setOnClickListener {

            btnMenuClick()
        }

        //1대1 문의하기
        binding.imageviewChatting.setOnClickListener {


            API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

            val tokenManager = com.example.meetup.sharedPreference.TokenManager(requireContext())


            try {
                API.postChatRoom(
                    tokenManager.getAccessToken().toString(),
                    MessageRequestDto(storeId)
                ).enqueue(
                    object : Callback<PostChatRoomResponseModel> {

                        override fun onResponse(
                            call: Call<PostChatRoomResponseModel>,
                            response: Response<PostChatRoomResponseModel>
                        ) {
                            if (response.isSuccessful) {


//
                                val roomId = response.body()!!.result.roomId
                                val senderName = response.body()!!.result.sender
                                Log.d("roomId", roomId.toString())

//                                chattingRoomViewModel.senderName.value = senderName
//                                chattingRoomViewModel.roomId.value = roomId

                                MyApplication.preferences.setString("roomId","$roomId")
                                MyApplication.preferences.setString("senderName","$senderName")


                                val chattingRoomFragment = ChattingRoomFragment()
                                fragmentManager?.beginTransaction()?.apply {
                                    replace(R.id.frameArea, chattingRoomFragment)
                                    addToBackStack(null)
                                    commit()
                                }

//                                val intent = Intent(context, ChattingActivity::class.java)
//
//                                intent.putExtra("roomId", roomId)
//                                intent.putExtra("senderName", senderName)
//                                startActivity(intent)
//                                Log.d(
//                                    "PostChatRoomResponseModel : ",
//                                    " success, ${response.body().toString()}"
//                                )

                            } else {

                                Log.d(
                                    "PostChatRoomResponseModel : ",
                                    "fail 1 ${
                                        response.body().toString()
                                    } , ${response.message()}, ${response.errorBody().toString()}"
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<PostChatRoomResponseModel>,
                            t: Throwable
                        ) {
                            Log.d(
                                "PostChatRoomResponseModel Response : ",
                                " fail 2 , ${t.message.toString()}"
                            )
                        }
                    })
            } catch (e: Exception) {
                Log.d("PostChatRoomResponseModel response : ", " fail 3 , ${e.message}")
            }


        }
        //가게 정보 클릭
        binding.btnStoreInfo.setOnClickListener {

            btnStoreInfoClick()
        }

        //공지사항 클릭
        binding.btnNotice.setOnClickListener {

            btnNoticeClick()
        }

        //리뷰 클릭
        binding.btnReview.setOnClickListener {

            btnReviewClick()
        }

        binding.btnBack.setOnClickListener {
            btnBackClick()
        }

        binding.textviewHeart.setOnClickListener {
            textviewHeartCLick()
        }
    }

    fun textviewHeartCLick() {


        if (isHeartCheck == true) {

        } else {
            val customDialogHeartFragment = CustomDialogHeartFragment()

            customDialogHeartFragment.show(requireFragmentManager(), "CustomDialogHeartFragment")

            isHeartCheck = true


            binding.imageviewHeart.setImageResource(R.drawable.ic_heart_fill)


            API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

            val tokenManager = com.example.meetup.sharedPreference.TokenManager(requireContext())

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

            Log.d("tokenManager", tokenManager.getAccessToken().toString())

            try {
                API.postHeart(tokenManager.getAccessToken().toString(), storeId.toInt()).enqueue(
                    object : Callback<GetStoreDetailResponseModel> {

                        override fun onResponse(
                            call: Call<GetStoreDetailResponseModel>,
                            response: Response<GetStoreDetailResponseModel>
                        ) {
                            if (response.isSuccessful) {

//

                                Log.d("click heart : ", " success")

                            } else {

                                Log.d(
                                    "click heart Response : ",
                                    "fail 1 ${
                                        response.body().toString()
                                    } , ${response.message()}, ${response.errorBody().toString()}"
                                )
                            }
                        }

                        override fun onFailure(
                            call: Call<GetStoreDetailResponseModel>,
                            t: Throwable
                        ) {
                            Log.d("click heart Response : ", " fail 2 , ${t.message.toString()}")
                        }
                    })
            } catch (e: Exception) {
                Log.d("click heart response : ", " fail 3 , ${e.message}")
            }

        }


    }

    fun btnBackClick() {
        val storeFragment = StoreFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.frameArea, storeFragment)
            addToBackStack(null)
            commit()
        }
    }

    fun btnMenuClick() {

        binding.btnMenu.setTextColor(Color.parseColor("#000000"))
        binding.btnStoreInfo.setTextColor(Color.parseColor("#6E7178"))
        binding.btnNotice.setTextColor(Color.parseColor("#6E7178"))
        binding.btnReview.setTextColor(Color.parseColor("#6E7178"))

        binding.btnMenu.setBackgroundResource(R.drawable.store_select_border)
        binding.btnStoreInfo.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnNotice.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnReview.setBackgroundResource(R.drawable.store_not_select_border)


        val storeDetailMenuFragment = StoreDetailMenuFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.store_detail_frame_area1, storeDetailMenuFragment)
            commit()
        }

    }

    fun btnStoreInfoClick() {

        binding.btnMenu.setTextColor(Color.parseColor("#6E7178"))
        binding.btnStoreInfo.setTextColor(Color.parseColor("#000000"))
        binding.btnNotice.setTextColor(Color.parseColor("#6E7178"))
        binding.btnReview.setTextColor(Color.parseColor("#6E7178"))

        binding.btnMenu.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnStoreInfo.setBackgroundResource(R.drawable.store_select_border)
        binding.btnNotice.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnReview.setBackgroundResource(R.drawable.store_not_select_border)

        val storeDetailInfoFragment = StoreDetailInfoFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.store_detail_frame_area1, storeDetailInfoFragment)
            commit()
        }

    }

    fun btnNoticeClick() {


        binding.btnMenu.setTextColor(Color.parseColor("#6E7178"))
        binding.btnStoreInfo.setTextColor(Color.parseColor("#6E7178"))
        binding.btnNotice.setTextColor(Color.parseColor("#000000"))
        binding.btnReview.setTextColor(Color.parseColor("#6E7178"))

        binding.btnMenu.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnStoreInfo.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnNotice.setBackgroundResource(R.drawable.store_select_border)
        binding.btnReview.setBackgroundResource(R.drawable.store_not_select_border)

        val storeDetailNoticeFragment = StoreDetailNoticeFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.store_detail_frame_area1, storeDetailNoticeFragment)
            commit()
        }

    }

    fun btnReviewClick() {

        binding.btnMenu.setTextColor(Color.parseColor("#6E7178"))
        binding.btnStoreInfo.setTextColor(Color.parseColor("#6E7178"))
        binding.btnNotice.setTextColor(Color.parseColor("#6E7178"))
        binding.btnReview.setTextColor(Color.parseColor("#000000"))

        binding.btnMenu.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnStoreInfo.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnNotice.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnReview.setBackgroundResource(R.drawable.store_select_border)

        val storeDetailReviewFragment = StoreDetailReviewFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.store_detail_frame_area1, storeDetailReviewFragment)
            commit()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}