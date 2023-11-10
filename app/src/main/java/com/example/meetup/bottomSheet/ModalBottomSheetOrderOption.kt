package com.example.meetup.bottomSheet

import DialogOrder
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.BottomSheetOrderBinding
import com.example.meetup.databinding.DialogOrderBinding
import com.example.meetup.databinding.RowOrderFoodBinding
import com.example.meetup.databinding.RowOrderOptionBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ModalBottomSheetOrderOption : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetOrderBinding
    var deliveryOption = ""
    lateinit var homeActivity: HomeActivity

//    override fun getTheme(): Int = R.style.RoundBottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        homeActivity = activity as HomeActivity

        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)
        val contentView = View.inflate(context, R.layout.bottom_sheet_order, null)
        bottomSheetDialog.setContentView(contentView)

        val behavior = BottomSheetBehavior.from(contentView.parent as View)
//
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

        // 배경을 흐려지게 설정
        dialog?.window?.setBackgroundDrawableResource(R.drawable.blur_background)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
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

                recyclerviewFood.run {
                    adapter = FoodRecyclerViewAdapter()

                    layoutManager = LinearLayoutManager(requireContext())
                }
            }

            buttonGoToCart.setOnClickListener {
//                activity?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dismiss()

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

            recyclerviewOption.run {
                adapter = OptionRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(requireContext())
            }
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
                        layoutOption.visibility = View.GONE
                        layoutDeliveryOption.visibility = View.VISIBLE
                        buttonOption.run {
                            visibility = View.VISIBLE
                            text = "기본 A 세트 [나물+김치]"
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
            return 4
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowOptionName.text = "기본 A 세트 [나물+김치]"
            holder.rowPrice.text = "10,000원"
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

            init {
                rowFoodName = rowBinding.textviewFoodName
                rowOptionName = rowBinding.textviewOptionName
                rowPrice = rowBinding.textviewFoodPrice
                rowCount = rowBinding.textviewFoodNum
                rowCountPlusButton = rowBinding.buttonPlus
                rowCountMinusButton = rowBinding.buttonMinus
                rowCloseButton = rowBinding.buttonClose
                count = rowBinding.textviewFoodNum.text.toString().toInt()

                rowBinding.root.setOnClickListener {
                    binding.run {
                        layoutOption.visibility = View.GONE
                        buttonOption.run {
                            visibility = View.VISIBLE
                            text = "기본 A 세트 [나물+김치]"
                            setTextSize(18.27f)
                            // 폰트 파일을 로드하여 Typeface 객체로 변환
                            val fontTypeface = resources.getFont(R.font.pretendard_semi_bold)
                            typeface = fontTypeface
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                            setBackgroundResource(R.drawable.layout_order_background)
                        }
                    }
                }

                rowCountMinusButton.setOnClickListener {
                    if(count > 1) {
                        count--
                    }
                    rowBinding.run {
                        textviewFoodNum.text = count.toString()
                    }
                }
                rowCountPlusButton.setOnClickListener {
                    count++
                    rowBinding.run {
                        textviewFoodNum.text = count.toString()
                    }
                }

                rowCloseButton.setOnClickListener {
                    // 해당 주문 내역 선택지 삭제
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
            return 4
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowFoodName.text = "밑반찬 세트"
            holder.rowOptionName.text = "기본 A 세트 [나물+김치]"
            holder.rowPrice.text = "10,000원"
            holder.rowCount.text = "1"
        }
    }
}