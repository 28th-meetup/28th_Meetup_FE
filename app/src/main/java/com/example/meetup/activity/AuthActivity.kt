package com.example.meetup.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meetup.R
import com.example.meetup.databinding.ActivityAuthBinding


class AuthActivity : AppCompatActivity() {

    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}