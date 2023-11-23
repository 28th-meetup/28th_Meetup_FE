package com.example.meetup.fragment

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meetup.R
import com.example.meetup.adapter.HomeSetAdapter
import com.example.meetup.databinding.FragmentHomeCategoryBinding
import com.example.meetup.Util.fromDpToPx
import com.example.meetup.activity.HomeActivity
import com.example.meetup.adapter.CategorySetAdapter
import com.example.meetup.model.Food
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.CategoryFoodViewModel

class HomeCategoryFragment : Fragment() {

    lateinit var binding: FragmentHomeCategoryBinding
    lateinit var homeActivity: HomeActivity

    lateinit var viewModel: CategoryFoodViewModel

    var categoryFoodList = mutableListOf<Food>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeCategoryBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        MyApplication.filtering = "추천순"

        viewModel = ViewModelProvider(homeActivity)[CategoryFoodViewModel::class.java]

        viewModel.run {
            categoryFoodInfoList.observe(homeActivity) {
                binding.buttonFilter.text = MyApplication.filtering

                categoryFoodList = it

                binding.run {
                    recyclerview.run {
                        adapter = CategorySetAdapter(homeActivity.manager, homeActivity, categoryFoodList)

                        layoutManager = GridLayoutManager(homeActivity, 2)
                    }
                }
            }
        }

        initView()

        binding.run {
            toolbar.run {
                title = "${MyApplication.category}"

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_back)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
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
                            transaction.addToBackStack("")
                            transaction.commit()
                        }

                        else -> {

                        }
                    }
                    true
                }
            }

            binding.buttonFilter.run {
                setOnClickListener {
                    val modalBottomSheet = ModalBottomSheetFragment(homeActivity)


                    modalBottomSheet.show(requireFragmentManager(), ModalBottomSheetFragment.TAG)
                }
            }
        }
        return binding.root
    }

    fun initView() {
        binding.run {
            homeActivity.hideBottomNavigation(true)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.buttonFilter.text = MyApplication.filtering
    }
}