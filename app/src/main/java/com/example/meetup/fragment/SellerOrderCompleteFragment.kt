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
import com.example.meetup.adapter.OrderCompleteAdapter
import com.example.meetup.adapter.OrderInProgressAdapter
import com.example.meetup.databinding.FragmentSellerOrderCompleteBinding
import com.example.meetup.model.OrderPreviewResponseList
import com.example.meetup.viewmodel.SellerOrderHistoryViewModel

class SellerOrderCompleteFragment : Fragment() {

    lateinit var binding: FragmentSellerOrderCompleteBinding
    lateinit var homeActivity: HomeActivity

    lateinit var viewModel: SellerOrderHistoryViewModel

    var orderHistory = mutableListOf<OrderPreviewResponseList>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSellerOrderCompleteBinding.inflate(inflater)
        homeActivity = activity as HomeActivity
        viewModel = ViewModelProvider(homeActivity)[SellerOrderHistoryViewModel::class.java]
        viewModel.run {
            orderCount.observe(homeActivity) {
                binding.textviewCount.text = it.toString() + "개"
            }
            orderHistoryList.observe(homeActivity) {
                orderHistory = it

                binding.run {
                    recyclerview.run {
                        adapter = OrderCompleteAdapter(orderHistory)

                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSellerOrderHistory(homeActivity, "completed")
    }
}