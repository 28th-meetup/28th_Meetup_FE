package com.example.meetup.activity

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.meetup.R
import com.example.meetup.adapter.ChattingAdapter
import com.example.meetup.adapter.StoreListAdapter
import com.example.meetup.databinding.ActivityChattingBinding
import com.example.meetup.databinding.ActivityHomeBinding
import com.example.meetup.model.chatting.ChattingDataModel
import com.example.meetup.viewmodel.ChattingRoomViewModel
import com.example.meetup.viewmodel.ChattingViewModel
import com.example.meetup.viewmodel.StoreListViewModel
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import java.util.ArrayList

class ChattingActivity : AppCompatActivity() {
    private lateinit var viewModel: ChattingRoomViewModel

    private lateinit var chattingAdapter: ChattingAdapter


    lateinit var binding: ActivityChattingBinding

    private lateinit var stompClient: StompClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_chatting)




        viewModel = ViewModelProvider(this).get(ChattingRoomViewModel::class.java)

        chattingAdapter = ChattingAdapter(ArrayList())

        binding.recyclerViewChatting.adapter = chattingAdapter
        binding.recyclerViewChatting.layoutManager = LinearLayoutManager(this)


        var roomId = intent.getStringExtra("roomId")

        var senderName = intent.getStringExtra("senderName")

        Log.d("roomId", "$roomId")





        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://3.39.37.33:8080/ws-stomp")
        stompClient.connect()


        viewModel.chattingData.observe(this, Observer {

            chattingAdapter = ChattingAdapter(it)

            binding.recyclerViewChatting.adapter = chattingAdapter

        })
        // Subscribe to the chat topic
        stompClient.topic("/sub/chat/room/${roomId}")
            .subscribe({ topicMessage ->
                // Handle received messages here
                val receivedMessage = topicMessage.payload


                Log.d("receivedMessage", receivedMessage.toString())
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
            sendData.put("sendTime", "")

//            viewModel.chattingData.value!!.add(ChattingDataModel("$senderName","$roomId",binding.edittextWriteChattingText.text.toString(),""))
            Log.d("SendData", sendData.toString())


//            viewModel.addData(
//                ChattingDataModel(
//                    "${senderName}",
//                    "${roomId}",
//                    binding.edittextWriteChattingText.text.toString(),
//                    ""
//                )
//            )
            Log.d("SendData viewmodel", sendData.toString())

            stompClient.send("/pub/message", sendData.toString()).subscribe()
            binding.edittextWriteChattingText.text.clear()

        }


    }

}