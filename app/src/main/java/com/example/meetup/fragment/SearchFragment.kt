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
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.adapter.SearchFoodAdapter
import com.example.meetup.adapter.SearchStoreAdapter
import com.example.meetup.databinding.FragmentSearchBinding
import com.example.meetup.databinding.RowSellerOrderInProgressBinding
import com.example.meetup.viewmodel.FoodMenuDetailViewModel
import com.example.meetup.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var homeActivity: HomeActivity

    lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        viewModel = ViewModelProvider(homeActivity)[SearchViewModel::class.java]
        viewModel.run {
            isExist.observe(homeActivity) {
                if(it) {
                    binding.layoutSearchList.visibility = View.VISIBLE
                    binding.layoutNoSearchList.visibility = View.GONE
                } else {
                    binding.layoutSearchList.visibility = View.GONE
                    binding.layoutNoSearchList.visibility = View.VISIBLE
                }
            }
            searchStoreList.observe(homeActivity) {
                binding.run {
                    recyclerviewSearchStore.run {
                        adapter = SearchStoreAdapter(homeActivity.manager, homeActivity, it)

                        layoutManager = LinearLayoutManager(homeActivity)
                    }
                }
            }
            searchFoodList.observe(homeActivity) {
                binding.run {
                    recyclerviewSearchFood.run {
                        adapter = SearchFoodAdapter(homeActivity.manager, homeActivity, it)

                        layoutManager = GridLayoutManager(homeActivity,2)
                    }
                }
            }
        }

        binding.run {
            searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.getSearchList(homeActivity, query!!)

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        initView()

        return binding.root
    }

    fun initView() {
        binding.run {
            toolbar.run {
                title = "검색 결과"

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_back)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
    }
}