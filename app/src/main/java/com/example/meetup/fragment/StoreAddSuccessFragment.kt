package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentStoreAddSuccessBinding

class StoreAddSuccessFragment : BaseFragment<FragmentStoreAddSuccessBinding>(R.layout.fragment_store_add_success) {


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

        binding.cardviewGoHome.setOnClickListener {
            val homeFragment = HomeFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, homeFragment)
                commit()
            }
        }

    }


}