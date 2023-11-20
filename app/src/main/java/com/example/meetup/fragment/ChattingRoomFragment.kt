package com.example.meetup.fragment

import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.R
import com.example.meetup.adapter.ChattingAdapter
import com.example.meetup.databinding.FragmentChattingBinding
import com.example.meetup.databinding.FragmentChattingRoomBinding
import com.example.meetup.model.chatting.ChattingDataModel
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.ChattingRoomViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import kotlin.collections.ArrayList


class ChattingRoomFragment : Fragment() {
    private var _binding: FragmentChattingRoomBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ChattingRoomViewModel

    private lateinit var chattingAdapter: ChattingAdapter

    private lateinit var stompClient: StompClient

    var chatArray = ArrayList<ChattingDataModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChattingRoomBinding.inflate(inflater, container, false)
        val view = binding.root



        viewModel = ViewModelProvider(requireActivity()).get(ChattingRoomViewModel::class.java)

        chattingAdapter = ChattingAdapter(ArrayList())

        binding.recyclerViewChatting.adapter = chattingAdapter
        binding.recyclerViewChatting.layoutManager = LinearLayoutManager(requireContext())

//
//        var roomId = intent.getStringExtra("roomId")
//
//        var senderName = intent.getStringExtra("senderName")
//
//        Log.d("roomId", "$roomId")



        var roomId = MyApplication.preferences.getString("roomId","")
        var senderName = MyApplication.preferences.getString("senderName","")


        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://3.39.37.33:8080/ws-stomp")
        stompClient.connect()

        viewModel.chattingData.observe(viewLifecycleOwner){
            chattingAdapter = ChattingAdapter(it)

            binding.recyclerViewChatting.adapter = chattingAdapter

        }

        stompClient.topic("/sub/chat/room/${roomId}")
            .subscribe({ topicMessage ->
                // Handle received messages here
                val receivedMessage = topicMessage.payload

                Log.d("receivedMessage", topicMessage.toString())
                // Update UI or handle the message
            }, { throwable ->
                // Handle errors here
                throwable.printStackTrace()
                // Add your custom error handling logic here
            })

        binding.imageviewSendChatting.setOnClickListener {

            val sendData = JSONObject()
            sendData.put("senderName", "${senderName}")
            sendData.put("roomId", "${roomId}")
            sendData.put("message", binding.edittextWriteChattingText.text.toString())
            sendData.put("sendTime", "0")



            chatArray.add(ChattingDataModel("${senderName}","${roomId}",binding.edittextWriteChattingText.text.toString(),"0"))


            Log.d("chatArray", chatArray.toString())



            viewModel.addData(chatArray)

            Log.d("SendData", sendData.toString())

            Log.d("viewmodel SendData", viewModel.chattingData.value.toString())


            stompClient.send("/pub/message", sendData.toString()).subscribe()
            binding.edittextWriteChattingText.text.clear()

        }


        return view
    }


}