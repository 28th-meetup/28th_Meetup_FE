package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.R
import com.example.meetup.adapter.OrderListAdapter
import com.example.meetup.adapter.StoreListAdapter
import com.example.meetup.databinding.FragmentOrderListBinding
import com.example.meetup.databinding.FragmentStoreDetailBinding
import com.example.meetup.model.OrderListResponseModel


class OrderListFragment : Fragment() {

    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderListAdapter: OrderListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOrderListBinding.inflate(inflater,container,false)
        val view = binding.root

        var orderList = ArrayList<OrderListResponseModel>()

        binding.recyclerviewOrderList.layoutManager = LinearLayoutManager(requireContext())


        


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
  }

