package com.example.meetup.activity

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import com.example.meetup.R
import com.example.meetup.databinding.ActivityChattingBinding
import com.example.meetup.databinding.ActivityHomeBinding
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

class ChattingActivity : AppCompatActivity() {



    lateinit var binding: ActivityChattingBinding

    private lateinit var stompClient: StompClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)

        setContentView(binding.root)



        var roomId = intent.getStringExtra("roomId")

        var senderName = intent.getStringExtra("senderName")

        Log.d("roomId","$roomId")


        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://3.39.37.33:8080/ws-stomp")
        stompClient.connect()

        // Subscribe to the chat topic
        stompClient.topic("/sub/chat/room/${roomId}")
            .subscribe ({ topicMessage ->
                // Handle received messages here
                val receivedMessage = topicMessage.payload

                Log.d("receivedMessage",receivedMessage.toString())
                // Update UI or handle the message
            }, { throwable ->
            // Handle errors here
            throwable.printStackTrace()
            // Add your custom error handling logic here
        })





        binding.imageviewSendChatting.setOnClickListener {

            val sendData = JSONObject()
            sendData.put("senderName","${senderName}")
            sendData.put("roomId","${roomId}")
            sendData.put("message",binding.edittextWriteChattingText.text.toString())
            sendData.put("sendTime","")

            Log.d("SendData", sendData.toString())
            stompClient.send("/pub/message", sendData.toString()).subscribe()
            binding.edittextWriteChattingText.text.clear()

        }


    }

}