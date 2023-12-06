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
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.R
import com.example.meetup.adapter.ChattingAdapter
import com.example.meetup.databinding.FragmentChattingBinding
import com.example.meetup.databinding.FragmentChattingRoomBinding
import com.example.meetup.model.GetChattingMessage
import com.example.meetup.model.GetChattingMessageResult
import com.example.meetup.model.chatting.ChattingDataModel
import com.example.meetup.retrofit2.APIS
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.ChattingRoomViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import kotlin.collections.ArrayList


class ChattingRoomFragment : Fragment() {

    private lateinit var API : APIS

    private var _binding: FragmentChattingRoomBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ChattingRoomViewModel

    private lateinit var chattingAdapter: ChattingAdapter

    private lateinit var stompClient: StompClient

    var chatArray = ArrayList<ChattingDataModel>()
     var s = ""
    var m = ""
    var r = ""
    var alreadychatList = ArrayList<GetChattingMessageResult>()
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




        var roomId = MyApplication.preferences.getString("roomId", "")
        var senderName = MyApplication.preferences.getString("senderName", "")

        var senderNameMine =  MyApplication.preferences.getString("senderNameMine","")


        //-------------------------------------------------------------------------








        //-------------------------------------------------------------------------

        Log.d("roomId real", roomId)
        Log.d("senderName ", senderName)
        Log.d("senderNameMine ", senderNameMine)
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://3.39.37.33:8080/ws-stomp")
        stompClient.connect()

        stompClient.topic("/sub/chat/room/${roomId}")
            .subscribe({ topicMessage ->
                // Handle received messages here
                val receivedMessage = topicMessage.payload

                viewModel =
                    ViewModelProvider(requireActivity()).get(ChattingRoomViewModel::class.java)
//                val chatResponse = Json.decodeFromString<ChattingDataModel>(receivedMessage)
//
//                Log.d("receivedMessage", receivedMessage.toString())
                Log.d("receivedMessage", receivedMessage)

                val chat = """${receivedMessage}""".trimIndent()

                var chatResponse = Gson().fromJson(chat, ChattingDataModel::class.java)

                Log.d(" received chatResponse", chatResponse.toString())
                Log.d(" chatResponse sendername", "${chatResponse.senderName}")


                if (chatResponse.senderName == senderNameMine) {
                    Log.d(" chatResponse sendername", "${chatResponse.senderName}")

                    Log.d(" chatResponse sendername", "Same!")

                } else {
                    chatArray.add(chatResponse)
                    Log.d(" received chatArray", chatArray.toString())


                }




                // Update UI or handle the message
            },
                { throwable ->
                    // Handle errors here
                    throwable.printStackTrace()
                    // Add your custom error handling logic here
                }


            ) {
                chattingAdapter = ChattingAdapter(chatArray)

                binding.recyclerViewChatting.adapter = chattingAdapter

//                chattingAdapter.notifyDataSetChanged()
//                viewModel.addData(chatArray)
//                Log.d(" received viewModel", viewModel.chattingData.value.toString())


//                notifyAll()
//                notify()
                viewModel.chattingData.observe(viewLifecycleOwner) {
                    chattingAdapter = ChattingAdapter(it)

                    binding.recyclerViewChatting.adapter = chattingAdapter

                }
            }

//        viewModel.addData(chatArray)

        binding.imageviewSendChatting.setOnClickListener {


            if (binding.edittextWriteChattingText.text.toString() != "") {
                val sendData = JSONObject()
                sendData.put("senderName", "${senderNameMine}")
                sendData.put("roomId", "${roomId}")
                sendData.put("message", binding.edittextWriteChattingText.text.toString())
//            sendData.put("sendTime", "0")


                chatArray.add(
                    ChattingDataModel(
                        "${senderNameMine}",
                        "${roomId}",
                        binding.edittextWriteChattingText.text.toString(),
                        true
                    )
                )

                Log.d("chatArray", chatArray.toString())

                Log.d("SendData", sendData.toString())

                Log.d("viewmodel SendData", viewModel.chattingData.value.toString())

                stompClient.send("/pub/message", sendData.toString()).subscribe()
                binding.edittextWriteChattingText.text.clear()
                chattingAdapter = ChattingAdapter(chatArray)

                binding.recyclerViewChatting.adapter = chattingAdapter

                chattingAdapter.notifyDataSetChanged()

            } else {

                viewModel.addData(chatArray)

            }

        }

        viewModel.chattingData.observe(viewLifecycleOwner) {


        chattingAdapter = ChattingAdapter(chatArray)

        binding.recyclerViewChatting.adapter = chattingAdapter

        chattingAdapter.notifyDataSetChanged()
        }


        binding.btnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}
