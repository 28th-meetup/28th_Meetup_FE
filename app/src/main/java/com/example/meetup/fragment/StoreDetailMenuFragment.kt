package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meetup.adapter.StoreDetailMenuAdapter
import com.example.meetup.databinding.FragmentStoreDetailMenuBinding
import com.example.meetup.model.store.StoreDetailMenuResponseModel

class StoreDetailMenuFragment : Fragment() {

    private var _binding: FragmentStoreDetailMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var storeDetailMenuAdapter: StoreDetailMenuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoreDetailMenuBinding.inflate(inflater,container,false)
        val view = binding.root

        val store_detail_menu_list = ArrayList<StoreDetailMenuResponseModel>()

        store_detail_menu_list.add(StoreDetailMenuResponseModel("a","menu1","000000원"))
        store_detail_menu_list.add(StoreDetailMenuResponseModel("a","menu2","000000원"))
        store_detail_menu_list.add(StoreDetailMenuResponseModel("a","menu3","000000원"))
        store_detail_menu_list.add(StoreDetailMenuResponseModel("a","menu4","000000원"))
        store_detail_menu_list.add(StoreDetailMenuResponseModel("a","menu5","000000원"))
        store_detail_menu_list.add(StoreDetailMenuResponseModel("a","menu6","000000원"))
        store_detail_menu_list.add(StoreDetailMenuResponseModel("a","menu7","000000원"))
        store_detail_menu_list.add(StoreDetailMenuResponseModel("a","menu8","000000원"))


        storeDetailMenuAdapter = StoreDetailMenuAdapter(store_detail_menu_list)

        binding.recyclerviewStoreDetailMenu.adapter = storeDetailMenuAdapter
        binding.recyclerviewStoreDetailMenu.layoutManager = GridLayoutManager(context,2)


        return view
    }


}