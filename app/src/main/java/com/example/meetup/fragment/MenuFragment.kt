package com.example.meetup.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBinding.inflate(inflater)

        binding.run {

            layoutDeliveryInfoValue.visibility = View.GONE
            layoutFoodInfoValue.visibility = View.GONE

            toolbar.run {
                inflateMenu(R.menu.main_cart_menu)

                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.item_main_cart -> {

                        }

                        else -> { }
                    }
                    true
                }
            }

        }

        return binding.root
    }
}