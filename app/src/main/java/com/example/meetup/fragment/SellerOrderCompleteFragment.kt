package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.R
import com.example.meetup.adapter.OrderCompleteAdapter
import com.example.meetup.databinding.FragmentSellerOrderCompleteBinding

class SellerOrderCompleteFragment : Fragment() {

    lateinit var binding: FragmentSellerOrderCompleteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSellerOrderCompleteBinding.inflate(inflater)

        initView()

        return binding.root
    }

    fun initView() {
        binding.run {
            recyclerview.run {
                adapter = OrderCompleteAdapter()

                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}