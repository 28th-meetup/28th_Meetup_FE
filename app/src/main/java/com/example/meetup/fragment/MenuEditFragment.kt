package com.example.meetup.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.R
import com.example.meetup.adapter.MenuListAdapter
import com.example.meetup.databinding.FragmentMenuEditBinding
import com.example.meetup.databinding.FragmentStoreDetailBinding
import com.example.meetup.model.MenuListResponseModel


class MenuEditFragment : Fragment() {

    private var _binding: FragmentMenuEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuListAdapter: MenuListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuEditBinding.inflate(inflater,container,false)
        val view = binding.root


        var menulist = ArrayList<MenuListResponseModel>()

        menulist.add(MenuListResponseModel("a","aaa","aa","1,000"))
        menulist.add(MenuListResponseModel("a","bbb","bbb","2,000"))
        menulist.add(MenuListResponseModel("a","cc","cc","3,000"))


//        menuListAdapter = MenuListAdapter(menulist)

        menuListAdapter = MenuListAdapter(menulist,
            itemClickListener1 ={

                                Log.d("menu_edit_itemclick",it.toString())

            } ,
            itemClickListener2 = {
                Log.d("menu_delete_itemclick",it.toString())

            })

        binding.recyclerviewMenuList.adapter = menuListAdapter

binding.recyclerviewMenuList.layoutManager = LinearLayoutManager(requireContext())


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