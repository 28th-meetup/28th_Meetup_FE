package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentStoreDetailBinding
import com.example.meetup.databinding.FragmentStoreDetailInfoBinding
import com.example.meetup.retrofit2.APIS
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.StoreListViewModel


class StoreDetailInfoFragment : Fragment() {
    private lateinit var viewModel: StoreListViewModel
    private lateinit var API: APIS
    private var _binding: FragmentStoreDetailInfoBinding? = null
    private val binding get() = _binding!!

    var storeId = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoreDetailInfoBinding.inflate(inflater,container,false)
        val view = binding.root


        viewModel = ViewModelProvider(requireActivity()).get(StoreListViewModel::class.java)
        storeId = MyApplication.preferences.getString("storeId", "").toLong()



        viewModel.getStoreDetail(requireContext(), storeId)

        viewModel.storeDetail.observe(viewLifecycleOwner){


            binding.textviewStoreName.text = it.result.storeDto.name
            binding.textviewStoreInfo.text = it.result.storeDto.description

        }


        return view



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}