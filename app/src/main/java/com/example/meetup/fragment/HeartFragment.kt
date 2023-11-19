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
import com.example.meetup.activity.HomeActivity
import com.example.meetup.adapter.HeartAdapter
import com.example.meetup.adapter.StoreListAdapter
import com.example.meetup.databinding.FragmentChattingBinding
import com.example.meetup.databinding.FragmentHeartBinding
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.HeartViewModel
import com.example.meetup.viewmodel.StoreListViewModel


class HeartFragment : Fragment() {
    private var _binding: FragmentHeartBinding? = null
    private val binding get() = _binding!!
    lateinit var homeActivity: HomeActivity
    private lateinit var heartAdapter: HeartAdapter
    private lateinit var viewModel: HeartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeActivity = activity as HomeActivity

        homeActivity.hideBottomNavigation(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentHeartBinding.inflate(inflater,container,false)
        val view = binding.root

        homeActivity = activity as HomeActivity

        viewModel = ViewModelProvider(requireActivity()).get(HeartViewModel::class.java)

        heartAdapter = HeartAdapter(ArrayList())

        binding.recyclerviewHeart.adapter = heartAdapter
        binding.recyclerviewHeart.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getHeartList(requireContext())

        viewModel.heartList.observe(viewLifecycleOwner){

            heartAdapter = HeartAdapter(it.result.stores)

            binding.recyclerviewHeart.adapter = heartAdapter

//            heartAdapter.itemClick = object : HeartAdapter.ItemClick {
//
//                override fun onClick(view: View, position: Int) {
//                    Log.d("storeId", it.result.stores[position].id.toString())
//
//
//                    MyApplication.preferences.setString("storeId", it.result.stores[position].id.toString())
//                    Log.d("storeId",it.result.stores[position].id.toString())
//                    goToStoreDetail(it.result.stores[position].id.toLong())
//
//                }
//            }

        }

        binding.imageviewAlarm.setOnClickListener {
            val alarmFragment = AlarmFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, alarmFragment)
                addToBackStack(null)
                commit()
            }
        }
        binding.imageviewCart.setOnClickListener {
            val cartFragment = CartFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, cartFragment)
                addToBackStack(null)
                commit()
            }
        }

        homeActivity.hideBottomNavigation(false)


        return view
    }

    fun goToStoreDetail(storeId : Long){

        val storeDetailFragment = StoreDetailFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.frameArea, storeDetailFragment)
            commit()
        }
    }
}