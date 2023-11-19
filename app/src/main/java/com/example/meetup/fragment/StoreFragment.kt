package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.activity.HomeActivity
import com.example.meetup.adapter.StoreListAdapter

import com.example.meetup.databinding.FragmentStoreBinding
import com.example.meetup.model.store.GetStoreListStoreDto
import com.example.meetup.model.store.GetStoreListStores
import com.example.meetup.model.store.StoreListResponseModel
import com.example.meetup.viewmodel.StoreListViewModel


class StoreFragment : Fragment() {


    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var storeListAdapter: StoreListAdapter

    lateinit var homeActivity: HomeActivity

    private lateinit var viewModel: StoreListViewModel

    private var menuchangelist = ArrayList<GetStoreListStores>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        val view = binding.root

        homeActivity = activity as HomeActivity
        homeActivity.hideBottomNavigation(false)

        viewModel = ViewModelProvider(requireActivity()).get(StoreListViewModel::class.java)

        storeListAdapter = StoreListAdapter(ArrayList())

        binding.recyclerviewStoreList.adapter = storeListAdapter
        binding.recyclerviewStoreList.layoutManager = LinearLayoutManager(requireContext())

        //초기 설정 전체
        viewModel.getStoreList(requireContext(), "", "bookmark", "DESC")

        viewModel.storeList.observe(viewLifecycleOwner) {

            storeListAdapter = StoreListAdapter(it.result.stores)
            binding.recyclerviewStoreList.adapter = storeListAdapter

            storeListAdapter.itemClick = object : StoreListAdapter.ItemClick {

                override fun onClick(view: View, position: Int) {
                    Log.d("storeId", it.result.stores[position].storeDto.id.toString())

                    val storeDetailFragment = StoreDetailFragment()
                    fragmentManager?.beginTransaction()?.apply {
                        replace(R.id.frameArea, storeDetailFragment)
                        addToBackStack(null)
                        commit()
                    }
                }
            }
        }


        //전체 클릭
        binding.btnAll.setOnClickListener {
            Log.d("btnAll", "click")

            viewModel.getStoreList(requireContext(), "", "bookmark", "DESC")

            viewModel.storeList.observe(viewLifecycleOwner) {

                storeListAdapter = StoreListAdapter(it.result.stores)
                binding.recyclerviewStoreList.adapter = storeListAdapter

                storeListAdapter.itemClick = object : StoreListAdapter.ItemClick {

                    override fun onClick(view: View, position: Int) {
                        Log.d("storeId", it.result.stores[position].storeDto.id.toString())

                        val storeDetailFragment = StoreDetailFragment()
                        fragmentManager?.beginTransaction()?.apply {
                            replace(R.id.frameArea, storeDetailFragment)
                            addToBackStack(null)
                            commit()
                        }
                    }
                }
            }
        }

        //메뉴변경형 클릭
        binding.btnMenuChange.setOnClickListener {

            Log.d("btnMenuChange", "click")
            viewModel.getStoreList(requireContext(), "", "bookmark", "DESC")

            viewModel.storeList.observe(viewLifecycleOwner) {

                for (i in 0..it.result.stores.size) {
                    if (it.result.stores[i].isFoodChangeable == true) {
                        menuchangelist = it.result.stores
                    }
                }
                storeListAdapter = StoreListAdapter(menuchangelist)

                binding.recyclerviewStoreList.adapter = storeListAdapter

                storeListAdapter.itemClick = object : StoreListAdapter.ItemClick {

                    override fun onClick(view: View, position: Int) {

                        Log.d("storeId", it.result.stores[position].storeDto.id.toString())
                        val storeDetailFragment = StoreDetailFragment()
                        fragmentManager?.beginTransaction()?.apply {
                            replace(R.id.frameArea, storeDetailFragment)
                            addToBackStack(null)
                            commit()
                        }
                    }
                }
            }
        }

        binding.btnAlarm.setOnClickListener {

            val alarmFragment = AlarmFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, alarmFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnCart.setOnClickListener {
            val cartFragment = CartFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, cartFragment)
                addToBackStack(null)
                commit()
            }
        }
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

        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//
//
//    }


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