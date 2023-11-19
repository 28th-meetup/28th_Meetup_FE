package com.example.meetup.fragment

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.bottomSheet.ModalBottomSheetOrderOption
import com.example.meetup.databinding.FragmentMenuBinding
import com.example.meetup.model.FoodIdResult
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.FoodMenuDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MenuFragment : Fragment() {

    lateinit var binding: FragmentMenuBinding
    lateinit var homeActivity: HomeActivity

    lateinit var viewModel: FoodMenuDetailViewModel

    var foodInfo = mutableListOf<FoodIdResult>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        viewModel = ViewModelProvider(homeActivity)[FoodMenuDetailViewModel::class.java]

        viewModel.run {
            foodMenuInfoList.observe(homeActivity) {
                foodInfo = it

                binding.run {
                    textviewFoodName.text = it.get(0).name
                    if(it.get(0).dollarPrice.toInt() == 0) {
                        textviewFoodPrice.text = it.get(0).canadaPrice.toString()
                    } else {
                        textviewFoodPrice.text = it.get(0).dollarPrice.toString()
                    }
                    textviewFoodInfoSummary.text = it.get(0).description
                    textviewFoodInfo2.text = it.get(0).ingredient
                    Glide.with(homeActivity).load(it.get(0).image).into(imageviewFood)
                    Glide.with(homeActivity).load(it.get(0).informationDescription).into(imageviewFoodInfo)
                }
            }
        }

        binding.run {

            initView()

            buttonOrder.setOnClickListener {
                viewModel.getFoodMenuOptionList(homeActivity, MyApplication.foodId)
                modalBottomSheet()
            }

            layoutDeliveryInfo.setOnClickListener {
                var deliveryInfo = layoutDeliveryInfoValue.visibility == View.GONE
                if(deliveryInfo) {
                    layoutDeliveryInfoValue.visibility = View.VISIBLE
                } else {
                    layoutDeliveryInfoValue.visibility = View.GONE
                }
            }

            layoutFoodInfo.setOnClickListener {
                var foodInfo = layoutFoodInfoValue.visibility == View.GONE
                if(foodInfo) {
                    layoutFoodInfoValue.visibility = View.VISIBLE
                } else {
                    layoutFoodInfoValue.visibility = View.GONE
                }
            }

            var fullText = textviewFoodPrice.text

            val spannableString = SpannableString(fullText)

            // 시작 인덱스와 끝 인덱스 사이의 텍스트에 다른 색상 적용
            val startIndex = textviewFoodPrice.text.length -1
            val endIndex = textviewFoodPrice.text.length
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#FF000000")), // 색상 설정
                startIndex, // 시작 인덱스
                endIndex, // 끝 인덱스 (exclusive)
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE // 스타일 적용 범위 설정
            )

            textviewFoodPrice.text = spannableString
        }

        return binding.root
    }

    fun initView() {
        binding.run {

            toolbar.run {

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_back)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    // 유저 인입경로별 뒤로가기 기능 구현
                    val homeFragment = HomeFragment()

                    val transaction = homeActivity.manager.beginTransaction()
                    transaction.replace(R.id.frameArea, homeFragment)
                    transaction.commit()
                }

                inflateMenu(R.menu.main_cart_menu)

                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.item_main_cart -> {
                            val cartFragment = CartFragment()

                            val transaction = homeActivity.manager.beginTransaction()
                            transaction.replace(R.id.frameArea, cartFragment)
                            transaction.commit()
                        }

                        else -> { }
                    }
                    true
                }
            }

            homeActivity.hideBottomNavigation(true)

            layoutDeliveryInfoValue.visibility = View.GONE
            layoutFoodInfoValue.visibility = View.GONE
        }
    }
    private fun modalBottomSheet() {
        val modal = ModalBottomSheetOrderOption(foodInfo)
        modal.show(homeActivity.supportFragmentManager, "주문하기 옵션")
    }
}