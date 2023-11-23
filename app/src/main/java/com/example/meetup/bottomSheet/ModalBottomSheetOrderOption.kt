package com.example.meetup.bottomSheet

import DialogOrder
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.BottomSheetOrderBinding
import com.example.meetup.databinding.DialogOrderBinding
import com.example.meetup.databinding.RowOrderFoodBinding
import com.example.meetup.databinding.RowOrderOptionBinding
import com.example.meetup.model.CartFood
import com.example.meetup.model.FoodIdResult
import com.example.meetup.model.MenuOptionResult
import com.example.meetup.viewmodel.FoodMenuDetailViewModel
import com.example.meetup.viewmodel.HomeFoodViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ModalBottomSheetOrderOption(var foodInfo: List<FoodIdResult>) : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetOrderBinding
    lateinit var homeActivity: HomeActivity
    lateinit var viewModel: FoodMenuDetailViewModel

    var foodMenuOptionList = mutableListOf<MenuOptionResult>()
    var selectFoodOptionList = mutableListOf<CartFood>()

    var deliveryOption = ""
    var selectedOptionName = ""
    var selectedOptionPrice = ""
    var selectedOptionId = 0L

    var foodInfoList = foodInfo

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        homeActivity = activity as HomeActivity
        binding = BottomSheetOrderBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(homeActivity)[FoodMenuDetailViewModel::class.java]

        viewModel.run {
            foodMenuOptionInfoList.observe(homeActivity) {
                binding.run {
                    foodMenuOptionList = it

                    Log.d("밋업", "${foodMenuOptionList}")

                    recyclerviewOption.run {
                        adapter = OptionRecyclerViewAdapter()

                        layoutManager = LinearLayoutManager(context)
                    }
                }
            }
        }

        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)
        val contentView = View.inflate(context, R.layout.bottom_sheet_order, null)
        bottomSheetDialog.setContentView(contentView)

        val behavior = BottomSheetBehavior.from(contentView.parent as View)

//        // Bottom Sheet의 높이를 고정하고자 하는 값으로 설정
//        behavior.peekHeight = resources.getDimensionPixelSize(R.dimen.bottom_sheet_height)
//
//        // Bottom Sheet의 상태를 COLLAPSED로 설정하여 높이를 고정시킵니다.
////        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        // 사용자가 Bottom Sheet를 축소할 수 없도록 설정
        behavior.skipCollapsed = true

        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = BottomSheetOrderBinding.inflate(inflater)

        initView()

//        val layoutParams = view?.layoutParams
//        layoutParams?.height = resources.getDimensionPixelSize(R.dimen.bottom_sheet_height) // 원하는 높이 값으로 설정
//        view?.layoutParams = layoutParams

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {

            textviewOptionValue.text = "${foodInfoList.get(0).name} 구성"

            buttonOption.setOnClickListener {
                buttonOption.visibility = View.GONE
                layoutOption.visibility = View.VISIBLE
                layoutDeliveryOption.visibility = View.GONE
                divider.visibility = View.GONE
                layoutTotal.visibility = View.GONE
                buttonGoToCart.visibility = View.GONE
            }

            buttonDeliveryOption.setOnClickListener {
                deliveryOption = "배달"
                layoutDeliveryOption.visibility = View.GONE
                divider.visibility = View.VISIBLE
                layoutTotal.visibility = View.VISIBLE
                buttonGoToCart.visibility = View.VISIBLE
                buttonOption.run {
                    visibility = View.VISIBLE
                    text = "옵션을 선택해주세요"
                    setTextSize(15.98f)
                    // 폰트 파일을 로드하여 Typeface 객체로 변환
                    val fontTypeface = resources.getFont(R.font.pretendard_regular)
                    typeface = fontTypeface
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.gray4))
                    setBackgroundResource(R.drawable.button_order_background)
                }

                var cartItem = CartFood(foodInfoList.get(0).storeId, foodInfoList.get(0).storeName, foodInfoList.get(0).categoryId, foodInfoList.get(0).image, foodInfoList.get(0).name, foodInfoList.get(0).id, selectedOptionName, selectedOptionId, deliveryOption, 1, selectedOptionPrice.toDouble(), true)
                selectFoodOptionList.add(cartItem)
                var totalFoodPrice = 0.0
                for(i in 0 until selectFoodOptionList.size) {
                    totalFoodPrice += (selectFoodOptionList.get(i).orderEachPrice.toDouble() * selectFoodOptionList.get(i).orderCount)
                }
                textviewTotalFoodPrice.text = "총 $ ${totalFoodPrice}"
                foodInfoList.get(0).foodPackage = "DELIVERY"
                Log.d("밋업", "foodInfo : ${foodInfoList}")
                recyclerviewFood.run {
                    adapter = FoodRecyclerViewAdapter()

                    layoutManager = LinearLayoutManager(requireContext())
                }
            }

            buttonPickupOption.setOnClickListener {
                deliveryOption = "포장"
                layoutDeliveryOption.visibility = View.GONE
                divider.visibility = View.VISIBLE
                layoutTotal.visibility = View.VISIBLE
                buttonGoToCart.visibility = View.VISIBLE
                buttonOption.run {
                    visibility = View.VISIBLE
                    text = "옵션을 선택해주세요"
                    setTextSize(15.98f)
                    // 폰트 파일을 로드하여 Typeface 객체로 변환
                    val fontTypeface = resources.getFont(R.font.pretendard_regular)
                    typeface = fontTypeface
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.gray4))
                    setBackgroundResource(R.drawable.button_order_background)
                }
                var cartItem = CartFood(foodInfoList.get(0).storeId, foodInfoList.get(0).storeName, foodInfoList.get(0).categoryId, foodInfoList.get(0).image, foodInfoList.get(0).name, foodInfoList.get(0).id, selectedOptionName, selectedOptionId, deliveryOption, 1, selectedOptionPrice.toDouble(), true)
                selectFoodOptionList.add(cartItem)
                var totalFoodPrice = 0.0
                for(i in 0 until selectFoodOptionList.size) {
                    totalFoodPrice += (selectFoodOptionList.get(i).orderEachPrice.toDouble() * selectFoodOptionList.get(i).orderCount)
                }
                textviewTotalFoodPrice.text = "총 $ ${totalFoodPrice}"
                foodInfoList.get(0).foodPackage = "PACKAGE"
                Log.d("밋업", "foodInfo : ${foodInfoList}")
                recyclerviewFood.run {
                    adapter = FoodRecyclerViewAdapter()

                    layoutManager = LinearLayoutManager(requireContext())
                }
            }

            buttonGoToCart.setOnClickListener {
//                activity?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dismiss()

                Log.d("밋업", "장바구니 : ${selectFoodOptionList}")
                val dialog = DialogOrder(selectFoodOptionList)
                // 알림창이 띄워져있는 동안 배경 클릭 막기
                dialog.isCancelable = false
                activity?.let { dialog.show(it.supportFragmentManager, "OrderDialog") }
            }
        }
    }

    fun initView() {
        binding.run {
            buttonOption.visibility = View.VISIBLE
            layoutOption.visibility = View.INVISIBLE
            layoutDeliveryOption.visibility = View.INVISIBLE
            divider.visibility = View.INVISIBLE
            layoutTotal.visibility = View.INVISIBLE
            buttonGoToCart.visibility = View.INVISIBLE
        }
    }

    inner class OptionRecyclerViewAdapter : RecyclerView.Adapter<OptionRecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowOrderOptionBinding) :
            RecyclerView.ViewHolder(rowBinding.root) {

            val rowOptionName: TextView
            val rowPrice: TextView

            init {
                rowOptionName = rowBinding.textviewOptionName
                rowPrice = rowBinding.textviewOptionPrice

                rowBinding.root.setOnClickListener {
                    binding.run {
                        selectedOptionName = foodMenuOptionList.get(adapterPosition).name.toString()
                        if(foodMenuOptionList.get(adapterPosition).dollarPrice.toInt() == 0) {
                            selectedOptionPrice = foodMenuOptionList.get(adapterPosition).canadaPrice.toString()
                        } else {
                            selectedOptionPrice = foodMenuOptionList.get(adapterPosition).dollarPrice.toString()
                        }
                        selectedOptionId = foodMenuOptionList.get(adapterPosition).id
                        layoutOption.visibility = View.GONE
                        layoutDeliveryOption.visibility = View.VISIBLE
                        if(foodInfoList.get(0).foodPackage == "DELIVERY") {
                            buttonPickupOption.visibility = View.GONE
                            dividerDeliveryOption.visibility = View.GONE
                        } else if(foodInfoList.get(0).foodPackage == "PACKAGE") {
                            buttonDeliveryOption.visibility = View.GONE
                            dividerDeliveryOption.visibility = View.GONE
                        }
                        buttonOption.run {
                            visibility = View.VISIBLE
                            text = "${selectedOptionName}"
                            setTextSize(18.27f)
                            // 폰트 파일을 로드하여 Typeface 객체로 변환
                            val fontTypeface = resources.getFont(R.font.pretendard_semi_bold)
                            typeface = fontTypeface
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                            setBackgroundResource(R.drawable.layout_order_background)
                        }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowOptionBinding = RowOrderOptionBinding.inflate(layoutInflater)
            val viewHolder = ViewHolderClass(rowOptionBinding)

            rowOptionBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolder
        }

        override fun getItemCount(): Int {
            return foodMenuOptionList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowOptionName.text = "${foodMenuOptionList.get(position).name}"
            if(foodMenuOptionList.get(position).dollarPrice.toInt() == 0) {
                holder.rowPrice.text = "${foodMenuOptionList.get(position).canadaPrice}"
            } else {
                holder.rowPrice.text = "$ ${foodMenuOptionList.get(position).dollarPrice}"
            }
        }
    }

    inner class FoodRecyclerViewAdapter : RecyclerView.Adapter<FoodRecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowOrderFoodBinding) :
            RecyclerView.ViewHolder(rowBinding.root) {

            var count = 0

            val rowFoodName : TextView
            val rowOptionName : TextView
            val rowPrice : TextView
            val rowCount : TextView
            val rowCountPlusButton : ImageButton
            val rowCountMinusButton : ImageButton
            val rowCloseButton : ImageButton
            val rowDeliveryOptionButton : Button

            init {
                rowFoodName = rowBinding.textviewFoodName
                rowOptionName = rowBinding.textviewOptionName
                rowPrice = rowBinding.textviewFoodPrice
                rowCount = rowBinding.textviewFoodNum
                rowCountPlusButton = rowBinding.buttonPlus
                rowCountMinusButton = rowBinding.buttonMinus
                rowCloseButton = rowBinding.buttonClose
                rowDeliveryOptionButton = rowBinding.buttonDeliveryOption
                count = rowBinding.textviewFoodNum.text.toString().toInt()

                rowBinding.root.setOnClickListener {

                }

                rowCountMinusButton.setOnClickListener {
                    if(count > 1) {
                        count--
                    }
                    selectFoodOptionList.get(adapterPosition).orderCount = count.toLong()
                    rowBinding.run {
                        textviewFoodNum.text = count.toString()
                        textviewFoodPrice.text = (count * selectFoodOptionList.get(adapterPosition).orderEachPrice).toString()
                    }
                    var totalFoodPrice = 0.0
                    for(i in 0 until selectFoodOptionList.size) {
                        totalFoodPrice += (selectFoodOptionList.get(i).orderEachPrice.toDouble() * selectFoodOptionList.get(i).orderCount)
                    }
                    binding.textviewTotalFoodPrice.text = "총 $ ${totalFoodPrice}"
                }
                rowCountPlusButton.setOnClickListener {
                    count++
                    selectFoodOptionList.get(adapterPosition).orderCount = count.toLong()
                    rowBinding.run {
                        textviewFoodNum.text = count.toString()
                        textviewFoodPrice.text = (count * selectFoodOptionList.get(adapterPosition).orderEachPrice).toString()
                    }
                    var totalFoodPrice = 0.0
                    for(i in 0 until selectFoodOptionList.size) {
                        totalFoodPrice += (selectFoodOptionList.get(i).orderEachPrice.toDouble() * selectFoodOptionList.get(i).orderCount)
                    }
                    binding.textviewTotalFoodPrice.text = "총 $ ${totalFoodPrice}"
                }

                rowCloseButton.setOnClickListener {
                    // 해당 주문 내역 선택지 삭제
                    selectFoodOptionList.removeAt(adapterPosition)
                    var adapter = binding.recyclerviewFood.adapter as FoodRecyclerViewAdapter

                    adapter.notifyDataSetChanged()
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowFoodBinding = RowOrderFoodBinding.inflate(layoutInflater)
            val viewHolder = ViewHolderClass(rowFoodBinding)

            rowFoodBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolder
        }

        override fun getItemCount(): Int {
            return selectFoodOptionList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowFoodName.text = "${selectFoodOptionList.get(position).foodName}"
            holder.rowOptionName.text = "${selectFoodOptionList.get(position).foodOptionName}"
            var totalPrice = selectFoodOptionList.get(position).orderCount * selectFoodOptionList.get(position).orderEachPrice
            holder.rowPrice.text = "$ ${totalPrice}"
            holder.rowCount.text = "${selectFoodOptionList.get(position).orderCount}"
            holder.rowDeliveryOptionButton.text = "${selectFoodOptionList.get(position).orderDeliveryOption}"
            if(selectFoodOptionList.get(position).orderDeliveryOption == "포장") {
                holder.rowDeliveryOptionButton.setBackgroundResource(R.drawable.button_pickup_background)
            } else {
                holder.rowDeliveryOptionButton.setBackgroundResource(R.drawable.button_delivery_background)
            }
        }
    }
}