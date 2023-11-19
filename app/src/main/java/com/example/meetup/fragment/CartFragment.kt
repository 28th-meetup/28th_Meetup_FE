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
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.bottomSheet.ModalBottomSheetOrderOption
import com.example.meetup.databinding.FragmentCartBinding
import com.example.meetup.databinding.RowCartBinding
import com.example.meetup.databinding.RowOrderOptionBinding
import com.example.meetup.model.FoodIdResult
import com.example.meetup.model.MenuOptionResult
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.FoodMenuDetailViewModel
import kotlin.concurrent.fixedRateTimer

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    lateinit var homeActivity: HomeActivity

    lateinit var viewModel: FoodMenuDetailViewModel

    var optionList = mutableListOf<MenuOptionResult>()
    var foodInfo = mutableListOf<FoodIdResult>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCartBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        viewModel = ViewModelProvider(homeActivity)[FoodMenuDetailViewModel::class.java]
        viewModel.run {
            foodMenuOptionInfoList.observe(homeActivity) {
                optionList = it

                modalBottomSheet()
            }
            foodMenuInfoList.observe(homeActivity) {
                foodInfo = it
            }
        }

        initView()

        binding.run {
            toolbar.run {
                title = "장바구니"

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_back)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    // 유저별 인입경로에 따른 화면 전환
                    fragmentManager?.popBackStack()
                }
            }
        }

        return binding.root
    }

    fun initView() {
        binding.run {

            homeActivity.hideBottomNavigation(true)

            recyclerview.run {
                adapter = RecyclerViewAdapter()

                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowCartBinding) :
            RecyclerView.ViewHolder(rowBinding.root) {

            var isChecked = false
            var count = 0

            var rowFoodImage: ImageView
            var rowFoodName: TextView
            var rowOptionName: TextView
            var rowPrice: TextView
            var rowCount: TextView
            var rowTotalPrice: TextView
            var rowPlus: ImageButton
            var rowMinus: ImageButton
            var rowClose: ImageButton
            var rowOptionChange: Button
            var rowDeliveryOption: Button
            var rowCheckBox: ImageButton

            init {
                rowFoodImage = rowBinding.imageview
                rowFoodName = rowBinding.textviewFoodName
                rowOptionName = rowBinding.textviewOptionName
                rowPrice = rowBinding.textviewFoodPrice
                rowCount = rowBinding.textviewFoodNum
                rowTotalPrice = rowBinding.textviewFoodTotalPrice
                rowPlus = rowBinding.buttonPlus
                rowMinus = rowBinding.buttonMinus
                rowClose = rowBinding.buttonClose
                rowOptionChange = rowBinding.buttonOptionChange
                rowDeliveryOption = rowBinding.buttonDeliveryOption
                rowCheckBox = rowBinding.buttonCheckbox
                count = rowBinding.textviewFoodNum.text.toString().toInt()

                rowCheckBox.setOnClickListener {
                    if(isChecked) {
                        rowBinding.buttonCheckbox.setImageResource(R.drawable.checkbox)
                        isChecked = false
                    } else {
                        rowBinding.buttonCheckbox.setImageResource(R.drawable.checkbox_fill)
                        isChecked = true
                    }
                }

                rowOptionChange.setOnClickListener {
                    viewModel.getFoodMenuInfo(homeActivity, MyApplication.foodId)
                    viewModel.getFoodMenuOptionList(homeActivity, MyApplication.foodId)
                }

                rowMinus.setOnClickListener {
                    if(count > 1) {
                        count--
                    }
                    rowBinding.run {
                        textviewFoodNum.text = count.toString()
                    }
                }
                rowPlus.setOnClickListener {
                    count++
                    rowBinding.run {
                        textviewFoodNum.text = count.toString()
                    }
                }

                rowClose.setOnClickListener {
                    // 해당 주문 내역 선택지 삭제
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowCartBinding = RowCartBinding.inflate(layoutInflater)
            val viewHolder = ViewHolderClass(rowCartBinding)

            rowCartBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolder
        }

        override fun getItemCount(): Int {
            return 4
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowOptionName.text = "기본 A 세트 [나물+김치]"
            holder.rowPrice.text = "10,000원"
        }
    }

    private fun modalBottomSheet() {
        val modal = ModalBottomSheetOrderOption(foodInfo)
        modal.show(homeActivity.supportFragmentManager, "주문하기 옵션")
    }
}