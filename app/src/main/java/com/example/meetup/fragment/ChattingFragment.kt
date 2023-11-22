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
import com.example.meetup.adapter.ChattingListAdapter
import com.example.meetup.adapter.StoreDetailMenuAdapter
import com.example.meetup.databinding.FragmentChattingBinding
import com.example.meetup.model.GetChatRoom
import com.example.meetup.model.MessageRequestDto
import com.example.meetup.model.chatting.ChattingListResponseModel
import com.example.meetup.model.chatting.PostChatRoomResponseModel
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.ChattingViewModel
import com.example.meetup.viewmodel.StoreListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChattingFragment : Fragment() {
    private lateinit var API: APIS

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

            chattingListAdapter.itemClick = object : ChattingListAdapter.ItemClick {

                override fun onClick(view: View, position: Int) {

                    API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

                    val tokenManager = com.example.meetup.sharedPreference.TokenManager(requireContext())

                    try {
                        API.getChatRoom(
                            tokenManager.getAccessToken().toString(),
                            it.result[position].roomId
                        ).enqueue(
                            object : Callback<GetChatRoom> {

                                override fun onResponse(
                                    call: Call<GetChatRoom>,
                                    response: Response<GetChatRoom>
                                ) {
                                    if (response.isSuccessful) {


//
                                        val roomId = response.body()!!.result.roomId
                                        val senderName = response.body()!!.result.senderName

                                        MyApplication.preferences.setString("roomId", roomId)
                                        MyApplication.preferences.setString("senderName",senderName)
                                        Log.d("roomId real", roomId.toString())

//
                                        val chattingRoomFragment = ChattingRoomFragment()
                                        fragmentManager?.beginTransaction()?.apply {
                                            replace(R.id.frameArea, chattingRoomFragment)
                                            addToBackStack(null)
                                            commit()
                                        }

//

                                    } else {

                                        Log.d(
                                            "GetChatRoom : ",
                                            "fail 1 ${
                                                response.body().toString()
                                            } , ${response.message()}, ${response.errorBody().toString()}"
                                        )
                                    }
                                }

                                override fun onFailure(
                                    call: Call<GetChatRoom>,
                                    t: Throwable
                                ) {
                                    Log.d(
                                        "GetChatRoom Response : ",
                                        " fail 2 , ${t.message.toString()}"
                                    )
                                }
                            })
                    } catch (e: Exception) {
                        Log.d("GetChatRoom response : ", " fail 3 , ${e.message}")
                    }


                }
            }

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