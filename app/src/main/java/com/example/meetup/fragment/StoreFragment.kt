package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import android.graphics.Color

import com.example.meetup.databinding.FragmentStoreBinding


class StoreFragment : Fragment() {


    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        val view = binding.root



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnAll.setOnClickListener {

            btnAll()
        }

        binding.btnMenuChange.setOnClickListener {


            btnMenuChange()
        }
    }


    fun btnAll() {
        binding.btnAll.setBackgroundResource(R.drawable.store_select_border)

        binding.btnAll.setTextColor(Color.parseColor("#1E1F23"))
        binding.btnMenuChange.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnMenuChange.setTextColor(Color.parseColor("#6E7178"))
    }

    fun btnMenuChange() {

        binding.btnMenuChange.setTextColor(Color.parseColor("#1E1F23"))

        binding.btnMenuChange.setBackgroundResource(R.drawable.store_select_border)


        binding.btnAll.setTextColor(Color.parseColor("#6E7178"))

        binding.btnAll.setBackgroundResource(R.drawable.store_not_select_border)

    }


}