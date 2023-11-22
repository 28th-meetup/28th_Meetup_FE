package com.example.meetup.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.adapter.OrderListAdapter
import com.example.meetup.adapter.StoreListAdapter
import com.example.meetup.databinding.FragmentOrderListBinding
import com.example.meetup.databinding.FragmentStoreDetailBinding
import com.example.meetup.model.OrderListResponseModel
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.OrderListViewModel
import com.example.meetup.viewmodel.StoreListViewModel


class OrderListFragment : Fragment() {

    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    lateinit var homeActivity: HomeActivity
    var clickPosition = 0

    var a = 0
    private lateinit var orderListAdapter: OrderListAdapter
    private lateinit var viewModel: OrderListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeActivity = activity as HomeActivity

        homeActivity.hideBottomNavigation(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        val view = binding.root

//        var orderList = ArrayList<OrderListResponseModel>()
//
//        orderList.add(OrderListResponseModel("A", "aa", "aaa", "Aa"))
//        orderList.add(OrderListResponseModel("b", "bb", "bbb", "bb"))
//        orderList.add(OrderListResponseModel("c", "cc", "ccc", "cc"))

//        orderListAdapter = OrderListAdapter(orderList) {
//
////            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
//
//
//            val reviewWriteFragment = ReviewWriteFragment()
//            fragmentManager?.beginTransaction()?.apply {
//                replace(R.id.frameArea, reviewWriteFragment)
//                commit()
//            }
//
//        }
        viewModel = ViewModelProvider(requireActivity()).get(OrderListViewModel::class.java)

        viewModel.getOrderList(requireContext())


        viewModel.orderList.observe(viewLifecycleOwner) {
            viewModel.setStoreId(it.result[clickPosition].id)


            orderListAdapter = OrderListAdapter(it.result, {


//                clickPosition = it
                MyApplication.preferences.setString("reviewKey",it.toString())

                Log.d("position",                "$it"
                )
//                MyApplication.preferences.getString("reviewKey","").toInt()


//                Log.d("clickPosition", "$it")

                val reviewWriteFragment = ReviewWriteFragment()
                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.frameArea, reviewWriteFragment)
                    commit()
                }
            }


            )
//            viewModel.setStoreId(it.result[MyApplication.preferences.getString("reviewKey","").toInt()].id)
//            var a = MyApplication.preferences.getString("reviewKey","").toInt()
            MyApplication.preferences.setString("reviewstoreId","${it.result[a].storeId}")

            MyApplication.preferences.setString("reviewName","${it.result[a].storeName}")
            MyApplication.preferences.setString("reviewimage","${it.result[a].storeImage}")

            MyApplication.preferences.setString("reviewTime","${it.result[a].orderedAt}")

            Log.d("setStoreId", "${viewModel.storeId.value}")
            binding.recyclerviewOrderList.adapter = orderListAdapter


        }




        orderListAdapter = OrderListAdapter(ArrayList(), {})
        binding.recyclerviewOrderList.adapter = orderListAdapter
        binding.recyclerviewOrderList.layoutManager = LinearLayoutManager(requireContext())





        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnBack.setOnClickListener {
            val myPageFragment = MyPageFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, myPageFragment)
                commit()
            }
        }
    }
}

