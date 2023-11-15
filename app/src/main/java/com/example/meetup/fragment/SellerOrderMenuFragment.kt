package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.R
import com.example.meetup.adapter.OrderMenuAdapter
import com.example.meetup.databinding.FragmentSellerOrderMenuBinding
import com.example.meetup.databinding.RowSellerOrderOptionBinding

class SellerOrderMenuFragment : Fragment() {

    lateinit var binding: FragmentSellerOrderMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSellerOrderMenuBinding.inflate(inflater)

        initView()

        binding.run {

        }

        return binding.root
    }

    fun initView() {
        binding.run {
            recyclerview.run {
                adapter = OrderMenuAdapter()

                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}