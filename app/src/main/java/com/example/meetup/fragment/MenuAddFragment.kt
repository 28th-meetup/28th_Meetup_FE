package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentMenuAddBinding


class MenuAddFragment : BaseFragment<FragmentMenuAddBinding>(R.layout.fragment_menu_add) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOptionAllAble.setOnClickListener {

            binding.btnOptionAllAble.strokeColor = android.graphics.Color.parseColor("#E60051")
            binding.btnOptionAllAbleText.setTextColor(android.graphics.Color.parseColor("#E60051"))

            binding.btnOptionOnlyDelivery.strokeColor = android.graphics.Color.parseColor("#ABBED1")
            binding.btnOptionOnlyDeliveryText.setTextColor(android.graphics.Color.parseColor("#000000"))

            binding.btnOptionOnlyTogo.strokeColor = android.graphics.Color.parseColor("#ABBED1")
            binding.btnOptionOnlyTogoText.setTextColor(android.graphics.Color.parseColor("#000000"))

        }

        binding.btnOptionOnlyDelivery.setOnClickListener {

            binding.btnOptionAllAble.strokeColor = android.graphics.Color.parseColor("#ABBED1")
            binding.btnOptionAllAbleText.setTextColor(android.graphics.Color.parseColor("#000000"))

            binding.btnOptionOnlyDelivery.strokeColor = android.graphics.Color.parseColor("#E60051")
            binding.btnOptionOnlyDeliveryText.setTextColor(android.graphics.Color.parseColor("#E60051"))

            binding.btnOptionOnlyTogo.strokeColor = android.graphics.Color.parseColor("#ABBED1")
            binding.btnOptionOnlyTogoText.setTextColor(android.graphics.Color.parseColor("#000000"))

        }

        binding.btnOptionOnlyTogo.setOnClickListener {

            binding.btnOptionAllAble.strokeColor = android.graphics.Color.parseColor("#ABBED1")
            binding.btnOptionAllAbleText.setTextColor(android.graphics.Color.parseColor("#000000"))

            binding.btnOptionOnlyDelivery.strokeColor = android.graphics.Color.parseColor("#ABBED1")
            binding.btnOptionOnlyDeliveryText.setTextColor(android.graphics.Color.parseColor("#000000"))

            binding.btnOptionOnlyTogo.strokeColor = android.graphics.Color.parseColor("#E60051")
            binding.btnOptionOnlyTogoText.setTextColor(android.graphics.Color.parseColor("#E60051"))

        }
    }

}