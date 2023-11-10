package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import android.graphics.Color
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.adapter.StoreListAdapter

import com.example.meetup.databinding.FragmentStoreBinding
import com.example.meetup.model.StoreListResponseModel


class StoreFragment : Fragment() {


    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var storeListAdapter: StoreListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        val view = binding.root


        val store_list = ArrayList<StoreListResponseModel>()

        store_list.add(StoreListResponseModel("a", "a", "a", "가게1", "100000원", "4.8"))
        store_list.add(StoreListResponseModel("a", "a", "a", "가게2", "100000원", "4.8"))
        store_list.add(StoreListResponseModel("a", "a", "a", "가게3", "100000원", "4.8"))

        storeListAdapter = StoreListAdapter(store_list)

        binding.recyclerviewStoreList.adapter = storeListAdapter
        binding.recyclerviewStoreList.layoutManager = LinearLayoutManager(requireContext())

        storeListAdapter.itemClick = object :StoreListAdapter.ItemClick {

            override fun onClick(view: View, position: Int) {

                val storeDetailFragment = StoreDetailFragment()
                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.frameArea, storeDetailFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }

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

        binding.btnFilter.setOnClickListener {
            val modalBottomSheet = ModalBottomSheetFragment()


            modalBottomSheet.show(requireFragmentManager(), ModalBottomSheetFragment.TAG)
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