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
import com.example.meetup.databinding.BottomSheetOrderOptionChangeBinding
import com.example.meetup.databinding.DialogOrderBinding
import com.example.meetup.databinding.RowOrderFoodBinding
import com.example.meetup.databinding.RowOrderOptionBinding
import com.example.meetup.fragment.CartFragment
import com.example.meetup.model.CartFood
import com.example.meetup.model.FoodIdResult
import com.example.meetup.model.MenuOptionResult
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.FoodMenuDetailViewModel
import com.example.meetup.viewmodel.HomeFoodViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ModalBottomSheetOrderOptionChange(var foodName: String, var cartNum: Int) : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetOrderOptionChangeBinding
    lateinit var homeActivity: HomeActivity
    lateinit var viewModel: FoodMenuDetailViewModel

    var foodMenuOptionList = mutableListOf<MenuOptionResult>()
    var selectFoodOptionList = mutableListOf<CartFood>()

    var selectedOptionName = ""
    var selectedOptionPrice = 0.0
    var selectedOptionId = 0L

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        homeActivity = activity as HomeActivity
        binding = BottomSheetOrderOptionChangeBinding.inflate(layoutInflater)
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

        binding = BottomSheetOrderOptionChangeBinding.inflate(inflater)

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
            textviewOptionValue.text = "${foodName} 구성"
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
                        if (foodMenuOptionList.get(adapterPosition).dollarPrice.toInt() == 0) {
                            selectedOptionPrice =
                                foodMenuOptionList.get(adapterPosition).canadaPrice
                        } else {
                            selectedOptionPrice =
                                foodMenuOptionList.get(adapterPosition).dollarPrice
                        }
                        selectedOptionId = foodMenuOptionList.get(adapterPosition).id
                    }
                    MyApplication.cartItem.get(cartNum).foodOptionName = selectedOptionName
                    MyApplication.cartItem.get(cartNum).foodOptionId = selectedOptionId
                    MyApplication.cartItem.get(cartNum).orderEachPrice = selectedOptionPrice
                    
                    dismiss()
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
}