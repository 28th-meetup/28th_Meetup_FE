package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.activity.HomeActivity
import com.example.meetup.adapter.ChattingListAdapter
import com.example.meetup.databinding.FragmentChattingBinding
import com.example.meetup.model.chatting.ChattingListResponseModel
import com.example.meetup.viewmodel.ChattingViewModel
import com.example.meetup.viewmodel.StoreListViewModel


class ChattingFragment : Fragment() {

    private var _binding: FragmentChattingBinding? = null
    private val binding get() = _binding!!

    lateinit var homeActivity : HomeActivity

    lateinit var viewModel : ChattingViewModel

    private lateinit var chattingListAdapter: ChattingListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeActivity = activity as HomeActivity

        homeActivity.hideBottomNavigation(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentChattingBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(ChattingViewModel::class.java)

//        var chattingList = ArrayList<ChattingListResponseModel>()
//
//        chattingList.add(ChattingListResponseModel("111", "111", "1111", "1111", "1"))
//        chattingList.add(ChattingListResponseModel("22", "22", "222", "222", "2"))
//        chattingList.add(ChattingListResponseModel("22", "333", "333", "333", "3"))
//

//        binding.searchview.setOnQueryTextListener(searchViewTextListener)
        chattingListAdapter = ChattingListAdapter(ArrayList())

        binding.recyclerviewChattingList.adapter = chattingListAdapter

        binding.recyclerviewChattingList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getChatList(requireContext())

        viewModel.chatList.observe(viewLifecycleOwner){

            chattingListAdapter = ChattingListAdapter(it.result)
            binding.recyclerviewChattingList.adapter = chattingListAdapter


        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {


            fragmentManager?.popBackStack()
        }
    }

//    var searchViewTextListener: SearchView.OnQueryTextListener =
//        object : SearchView.OnQueryTextListener {
//            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
//            override fun onQueryTextSubmit(s: String): Boolean {
//                return false
//            }
//
//            //텍스트 입력/수정시에 호출
//            override fun onQueryTextChange(s: String): Boolean {
//                chattingListAdapter.getFilter().filter(s)
//                Log.d("Aaa", "SearchVies Text is changed : $s")
//
//                return false
//            }
//        }
}