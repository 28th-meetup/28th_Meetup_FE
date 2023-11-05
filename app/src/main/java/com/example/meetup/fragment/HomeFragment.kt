package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var homeActivity: HomeActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        homeActivity = activity as HomeActivity

        initView()

        binding.run {
            toolbar.run {
                inflateMenu(R.menu.home_menu)

                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.item_alert -> {

                        }

                        R.id.item_cart -> {

                        }

                        else -> { }
                    }
                    true
                }
            }
        }
        return binding.root
    }

    fun initView() {
        binding.run {
            recyclerviewCategory.run {
                var categoryNameList: Array<String> = requireContext()?.resources!!.getStringArray(R.array.category_name)
                adapter = CategoryAdapter(categoryNameList, homeActivity.manager)
                layoutManager = GridLayoutManager(requireContext(),5)

                addItemDecoration(CategoryAdapter.GridSpaceItemDecoration(5,20))
            }

            recyclerviewTop10.run {
                adapter = HomeTopAdapter()
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            }

            recyclerviewSet.run {
                adapter = HomeSetAdapter()
                layoutManager = GridLayoutManager(requireContext(),2)

                val spanCount = 2
                val space = 22.83f.fromDpToPx()
                addItemDecoration(HomeSetAdapter.GridSpacingItemDecoration(spanCount, space, false))

            }
        }
    }
}