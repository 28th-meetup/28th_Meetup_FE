package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.meetup.model.BestSellingFoodList
import com.example.meetup.model.Food
import com.example.meetup.model.RecentSetFoodList
import com.example.meetup.viewmodel.CategoryFoodViewModel
import com.example.meetup.viewmodel.HomeFoodViewModel


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var homeActivity: HomeActivity

    lateinit var viewModel: HomeFoodViewModel

    var topFoodList = mutableListOf<BestSellingFoodList>()
    var setFoodList = mutableListOf<RecentSetFoodList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        homeActivity = activity as HomeActivity

        viewModel = ViewModelProvider(homeActivity)[HomeFoodViewModel::class.java]

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
                    var region = resources.getStringArray(R.array.location_array).get(it)
                    textviewRegion.text = region
                }
            }
        }

        viewModel.getHomeFoodInfo(requireContext())

        initView()

        binding.run {
            btnCart.setOnClickListener {
                val cartFragment = CartFragment()

                val transaction = homeActivity.manager.beginTransaction()
                transaction.replace(R.id.frameArea, cartFragment)
                transaction.addToBackStack("")
                transaction.commit()
            }
        }
        return binding.root
    }

    fun initView() {
        binding.run {
            recyclerviewCategory.run {
                var categoryNameList: Array<String> = requireContext()?.resources!!.getStringArray(R.array.category_name)
                adapter = CategoryAdapter(categoryNameList, homeActivity.manager, homeActivity)
                layoutManager = GridLayoutManager(requireContext(),5)

//                addItemDecoration(CategoryAdapter.GridSpaceItemDecoration(5,20))
            }

            homeActivity.hideBottomNavigation(false)
        }
    }
}