package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.adapter.OrderInProgressAdapter
import com.example.meetup.adapter.OrderMenuAdapter
import com.example.meetup.databinding.FragmentSellerOrderMenuBinding
import com.example.meetup.databinding.RowSellerOrderOptionBinding
import com.example.meetup.model.ProcessingFoodList
import com.example.meetup.viewmodel.SellerOrderHistoryMenuViewModel

class SellerOrderMenuFragment : Fragment() {

    lateinit var binding: FragmentSellerOrderMenuBinding
    lateinit var homeActivity: HomeActivity

    lateinit var viewModel: SellerOrderHistoryMenuViewModel

    var orderHistory = mutableListOf<ProcessingFoodList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSellerOrderMenuBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        viewModel = ViewModelProvider(homeActivity)[SellerOrderHistoryMenuViewModel::class.java]
        viewModel.run {
            orderCount.observe(homeActivity) {
                binding.textviewCount.text = it.toString() + "개"
            }
            orderHistoryList.observe(homeActivity) {
                orderHistory = it

                binding.run {
                    recyclerview.run {
                        adapter = OrderMenuAdapter(orderHistory)

                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSellerOrderHistory(homeActivity)
    }
}