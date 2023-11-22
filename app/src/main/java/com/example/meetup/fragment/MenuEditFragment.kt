package com.example.meetup.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.R
import com.example.meetup.adapter.MenuListAdapter
import com.example.meetup.databinding.FragmentMenuEditBinding
import com.example.meetup.databinding.FragmentStoreDetailBinding
import com.example.meetup.model.MenuListResponseModel
import com.example.meetup.viewmodel.MenuListViewModel
import com.example.meetup.viewmodel.StoreListViewModel
import java.util.ArrayList


class MenuEditFragment : Fragment() {

    private var _binding: FragmentMenuEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuListAdapter: MenuListAdapter

    private lateinit var viewModel: MenuListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuEditBinding.inflate(inflater,container,false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(MenuListViewModel::class.java)



        menuListAdapter = MenuListAdapter(ArrayList(),
            itemClickListener1 = {},
            itemClickListener2 = {

            })

        binding.recyclerviewMenuList.adapter = menuListAdapter

binding.recyclerviewMenuList.layoutManager = LinearLayoutManager(requireContext())


        viewModel.getMenu(requireContext())

        viewModel.storeDetailMenuList.observe(viewLifecycleOwner){

            menuListAdapter = MenuListAdapter(it.result,
                itemClickListener1 = {},
                itemClickListener2 = {
//                    viewModel.storeDetailMenuList.value!!.result.removeAt(it)
//                    Log.d("itemClickListener2",it.toString())


                })

            binding.recyclerviewMenuList.adapter = menuListAdapter

        }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //플로팅 버튼 클릭
        binding.floatingPlus.setOnClickListener {
            val menuAddFragment = MenuAddFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, menuAddFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

}