package com.example.meetup.fragment

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.bottomSheet.ModalBottomSheetOrderOption
import com.example.meetup.bottomSheet.ModalBottomSheetOrderOptionChange
import com.example.meetup.databinding.FragmentCartBinding
import com.example.meetup.databinding.RowCartBinding
import com.example.meetup.databinding.RowOrderOptionBinding
import com.example.meetup.model.FoodIdResult
import com.example.meetup.model.MenuOptionResult
import com.example.meetup.model.OrderFoodResponseModel
import com.example.meetup.model.SignInResponseModel
import com.example.meetup.model.request.OrderFoodDetailList
import com.example.meetup.model.request.OrderFoodRequestModel
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.sharedPreference.TokenManager
import com.example.meetup.viewmodel.CategoryFoodViewModel
import com.example.meetup.viewmodel.FoodMenuDetailViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.fixedRateTimer

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    lateinit var homeActivity: HomeActivity

    private val APIS = RetrofitInstance.retrofitInstance().create(com.example.meetup.retrofit2.APIS::class.java)

    lateinit var viewModel: FoodMenuDetailViewModel
    lateinit var homeViewModel: CategoryFoodViewModel

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
            }
            foodMenuInfoList.observe(homeActivity) {
                foodInfo = it
            }
        }
        homeViewModel = ViewModelProvider(homeActivity)[CategoryFoodViewModel::class.java]

        initView()

        Log.d("장바구니", "MyApplication : ${MyApplication.cartItem}")

        binding.run {
            buttonGoToStore.setOnClickListener {
                // 상품 선택(목록) 페이지로 이동
                homeViewModel.getCategoryFoodInfo(homeActivity, "RECOMMENDED",
                    MyApplication.cartItem.get(0).categoryId.toInt()
                )
                MyApplication.category = resources.getStringArray(R.array.category_name).get(MyApplication.cartItem.get(0).categoryId.toInt())
                val foodFragment = HomeCategoryFragment()

                val transaction = homeActivity.manager.beginTransaction()
                transaction.replace(R.id.frameArea, foodFragment)
                transaction.commit()
            }

            buttonOrder.setOnClickListener {
                orderFood()
            }
        }

        return binding.root
    }

    fun initView() {
        binding.run {

            homeActivity.hideBottomNavigation(true)

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
                    Log.d("장바구니", "MyApplication : ${MyApplication.cartItem}")
                    // 유저별 인입경로에 따른 화면 전환
                    fragmentManager?.popBackStack()
                }
            }

            if(MyApplication.cartItem.size != 0) {
                textviewStoreName.text = MyApplication.cartItem[0].storeName.toString()
            } else {
                textviewStoreName.text = ""
            }

            var totalFoodPrice = 0.0
            for(i in 0 until MyApplication.cartItem.size) {
                if(MyApplication.cartItem.get(i).isChecked) {
                    totalFoodPrice += (MyApplication.cartItem.get(i).orderEachPrice.toDouble() * MyApplication.cartItem.get(i).orderCount)
                }
            }
            textviewOrderTotalPrice.text = "총 ${totalFoodPrice}"

            recyclerviewCart.run {
                adapter = RecyclerViewAdapter()

                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowCartBinding) :
            RecyclerView.ViewHolder(rowBinding.root) {

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

                rowCheckBox.setOnClickListener {
                    if(MyApplication.cartItem.get(adapterPosition).isChecked) {
                        rowBinding.buttonCheckbox.setImageResource(R.drawable.checkbox)
                        MyApplication.cartItem.get(adapterPosition).isChecked = false
                    } else {
                        rowBinding.buttonCheckbox.setImageResource(R.drawable.checkbox_fill)
                        MyApplication.cartItem.get(adapterPosition).isChecked = true
                    }
                    var totalFoodPrice = 0.0
                    for(i in 0 until MyApplication.cartItem.size) {
                        if(MyApplication.cartItem.get(i).isChecked) {
                            totalFoodPrice += (MyApplication.cartItem.get(i).orderEachPrice.toDouble() * MyApplication.cartItem.get(i).orderCount)
                        }
                    }
                    binding.textviewOrderTotalPrice.text = "총 ${totalFoodPrice}"
                }

                rowOptionChange.setOnClickListener {
                    viewModel.getFoodMenuInfo(homeActivity, MyApplication.cartItem.get(adapterPosition).foodId.toInt())
                    viewModel.getFoodMenuOptionList(homeActivity, MyApplication.cartItem.get(adapterPosition).foodId.toInt())
                    modalBottomSheet(MyApplication.cartItem.get(adapterPosition).foodName, adapterPosition)

                    var adapter = binding.recyclerviewCart.adapter as RecyclerViewAdapter
                    adapter.notifyDataSetChanged()
                }

                rowMinus.setOnClickListener {
                    count = MyApplication.cartItem.get(adapterPosition).orderCount.toInt()
                    if(count > 1) {
                        count--
                        MyApplication.cartItem.get(adapterPosition).orderCount = count.toLong()
                    }
                    rowBinding.run {
                        textviewFoodNum.text = count.toString()
                        textviewFoodTotalPrice.text = (count * MyApplication.cartItem.get(adapterPosition).orderEachPrice).toString()
                    }
                    var totalFoodPrice = 0.0
                    for(i in 0 until MyApplication.cartItem.size) {
                        if(MyApplication.cartItem.get(i).isChecked) {
                            totalFoodPrice += (MyApplication.cartItem.get(i).orderEachPrice.toDouble() * MyApplication.cartItem.get(i).orderCount)
                        }
                    }
                    binding.textviewOrderTotalPrice.text = "총 ${totalFoodPrice}"
                }
                rowPlus.setOnClickListener {
                    count = MyApplication.cartItem.get(adapterPosition).orderCount.toInt()
                    count++
                    MyApplication.cartItem.get(adapterPosition).orderCount = count.toLong()
                    rowBinding.run {
                        textviewFoodNum.text = count.toString()
                        textviewFoodTotalPrice.text = (count * MyApplication.cartItem.get(adapterPosition).orderEachPrice).toString()
                    }
                    var totalFoodPrice = 0.0
                    for(i in 0 until MyApplication.cartItem.size) {
                        if(MyApplication.cartItem.get(i).isChecked) {
                            totalFoodPrice += (MyApplication.cartItem.get(i).orderEachPrice.toDouble() * MyApplication.cartItem.get(i).orderCount)
                        }
                    }
                    binding.textviewOrderTotalPrice.text = "총 ${totalFoodPrice}"
                }

                rowClose.setOnClickListener {
                    // 해당 주문 내역 선택지 삭제
                    MyApplication.cartItem.removeAt(adapterPosition)
                    var adapter = binding.recyclerviewCart.adapter as RecyclerViewAdapter
                    adapter.notifyDataSetChanged()
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
            return MyApplication.cartItem.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            Glide.with(context!!).load(MyApplication.cartItem.get(position).foodImage).into(holder.rowFoodImage)
            holder.rowFoodName.text = "${MyApplication.cartItem.get(position).foodName}"
            holder.rowOptionName.text = "${MyApplication.cartItem.get(position).foodOptionName}"
            holder.rowPrice.text = "개당가격 : ${MyApplication.cartItem.get(position).orderEachPrice}"
            holder.rowCount.text = "${MyApplication.cartItem.get(position).orderCount}"
            var totalPrice = MyApplication.cartItem.get(position).orderCount * MyApplication.cartItem.get(position).orderEachPrice
            holder.rowTotalPrice.text = totalPrice.toString()
            holder.rowDeliveryOption.text = "${MyApplication.cartItem.get(position).orderDeliveryOption}"
            if(MyApplication.cartItem.get(position).orderDeliveryOption == "포장") {
                holder.rowDeliveryOption.setBackgroundResource(R.drawable.button_pickup_background)
            } else {
                holder.rowDeliveryOption.setBackgroundResource(R.drawable.button_delivery_background)
            }
        }
    }

    fun orderFood() {

        var tokenManager = TokenManager(homeActivity)

        APIS.orderFood(tokenManager.getAccessToken().toString(), checkOrder()).enqueue(object :
            Callback<OrderFoodResponseModel> {
            override fun onResponse(
                call: Call<OrderFoodResponseModel>,
                response: Response<OrderFoodResponseModel>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: OrderFoodResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    val completeFragment = OrderCompleteFragment()

                    val transaction = homeActivity.manager.beginTransaction()
                    transaction.replace(R.id.frameArea, completeFragment)
                    transaction.commit()
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.body())

                    if (response.code() == 400) {

                    }
                }
            }

            override fun onFailure(call: Call<OrderFoodResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }

    fun checkOrder(): OrderFoodRequestModel {
        var totalCount = 0
        var store = 0L
        var totalPrice = 0L
        var selectedOption = ""
        var foodList = mutableListOf<OrderFoodDetailList>()
        for(i in 0 until MyApplication.cartItem.size) {
            if(MyApplication.cartItem.get(i).isChecked) {
                totalCount += MyApplication.cartItem.get(i).orderCount.toInt()
                store = MyApplication.cartItem.get(i).storeId
                totalPrice += (MyApplication.cartItem.get(i).orderEachPrice * MyApplication.cartItem.get(i).orderCount)
                if(MyApplication.cartItem.get(i).orderDeliveryOption == "배달") {
                    selectedOption = "delivery"
                } else {
                    selectedOption = "package"
                }
                foodList.add(OrderFoodDetailList(MyApplication.cartItem.get(i).foodId, MyApplication.cartItem.get(i).foodOptionId, MyApplication.cartItem.get(i).orderCount, MyApplication.cartItem.get(i).orderEachPrice))
            }
        }

        Log.d("밋업", "order food : ${OrderFoodRequestModel(store, totalPrice,
            totalCount.toLong(), selectedOption, foodList)}")
        return OrderFoodRequestModel(store, totalPrice,
            totalCount.toLong(), selectedOption, foodList)
    }

    private fun modalBottomSheet(foodName: String, position: Int) {
        val modal = ModalBottomSheetOrderOptionChange(foodName, position)
        modal.show(homeActivity.supportFragmentManager, "주문하기 옵션")
    }
}