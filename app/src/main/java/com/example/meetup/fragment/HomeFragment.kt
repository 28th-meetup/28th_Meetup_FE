package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.adapter.CategoryAdapter
import com.example.meetup.adapter.HomeSetAdapter
import com.example.meetup.adapter.HomeTopAdapter
import com.example.meetup.databinding.FragmentHomeBinding
import com.example.meetup.Util.fromDpToPx
import com.example.meetup.activity.HomeActivity
import com.example.meetup.dialog.DialogOrderCancel
import com.example.meetup.model.BestSellingFoodList
import com.example.meetup.model.Food
import com.example.meetup.model.OrderPreviewResponseList
import com.example.meetup.model.RecentSetFoodList
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.CategoryFoodViewModel
import com.example.meetup.viewmodel.FoodMenuDetailViewModel
import com.example.meetup.viewmodel.HomeFoodViewModel
import com.example.meetup.viewmodel.SearchViewModel
import com.example.meetup.viewmodel.SellerOrderHistoryViewModel


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var homeActivity: HomeActivity

    lateinit var viewModel: HomeFoodViewModel
    lateinit var searchViewModel: SearchViewModel
    lateinit var orderHistoryViewModel: SellerOrderHistoryViewModel

    var topFoodList = mutableListOf<BestSellingFoodList>()
    var setFoodList = mutableListOf<RecentSetFoodList>()

    var categoryNameList = listOf<String>()
    var locationList = listOf<String>()
    var orderHistory = mutableListOf<OrderPreviewResponseList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        homeActivity = activity as HomeActivity

        categoryNameList = homeActivity.resources!!.getStringArray(R.array.category_name).toList()
        locationList = homeActivity.resources!!.getStringArray(R.array.location_array).toList()

        viewModel = ViewModelProvider(homeActivity)[HomeFoodViewModel::class.java]
        searchViewModel = ViewModelProvider(homeActivity)[SearchViewModel::class.java]
        orderHistoryViewModel = ViewModelProvider(homeActivity)[SellerOrderHistoryViewModel::class.java]

        viewModel.run {
            topFoodInfoList.observe(homeActivity) {
                topFoodList = it

                binding.run {
                    recyclerviewTop10.run {
                        adapter = HomeTopAdapter(homeActivity.manager, homeActivity, topFoodList)
                        layoutManager = LinearLayoutManager(homeActivity, RecyclerView.HORIZONTAL, false)
                    }
                }
            }
            setFoodInfoList.observe(homeActivity) {
                setFoodList = it

                binding.run {
                    recyclerviewSet.run {
                        adapter = HomeSetAdapter(homeActivity.manager, homeActivity, setFoodList)
                        layoutManager = GridLayoutManager(homeActivity,2)

//                        val spanCount = 2
//                        val space = 22.83f.fromDpToPx()
//                        addItemDecoration(HomeSetAdapter.GridSpacingItemDecoration(spanCount, space, false))

                    }
                }
            }
            regionId.observe(homeActivity) {
                binding.run {
                    MyApplication.regionId = it.toInt()
                    textviewRegion.text = locationList.get(it)
                }
            }
        }

        orderHistoryViewModel.run {
            orderHistoryList.observe(homeActivity) {
                orderHistory = it

                if(orderHistory.size != 0) {
                    val dialog = OrderRequestDialogFragment(orderHistory)
                    // 알림창이 띄워져있는 동안 배경 클릭 막기
                    dialog.isCancelable = false
                    activity?.let { dialog.show(homeActivity.manager, "OrderDialog") }
                }
            }
        }

        viewModel.getHomeFoodInfo(homeActivity)
        orderHistoryViewModel.getHomeSellerOrderHistory(homeActivity, "pending")

        initView()

        binding.run {
            btnCart.setOnClickListener {
                val cartFragment = CartFragment()

                val transaction = homeActivity.manager.beginTransaction()
                transaction.replace(R.id.frameArea, cartFragment)
                transaction.addToBackStack("")
                transaction.commit()
            }

            searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchViewModel.getSearchList(homeActivity, query!!)

                    val searchFragment = SearchFragment()

                    val transaction = homeActivity.manager.beginTransaction()
                    transaction.replace(R.id.frameArea, searchFragment)
                    transaction.addToBackStack("")
                    transaction.commit()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        return binding.root
    }

    fun initView() {
        binding.run {
            recyclerviewCategory.run {
                adapter = CategoryAdapter(categoryNameList.toTypedArray(), homeActivity.manager, homeActivity)
                layoutManager = GridLayoutManager(requireContext(),5)

//                addItemDecoration(CategoryAdapter.GridSpaceItemDecoration(5,20))
            }

            homeActivity.hideBottomNavigation(false)
        }
    }
}