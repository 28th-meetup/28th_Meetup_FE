package com.example.meetup.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meetup.R
import com.example.meetup.databinding.ActivityAuthBinding
import com.example.meetup.fragment.LoginFragment
import com.example.meetup.fragment.SignUpAddressFragment
import com.example.meetup.fragment.SignUpFragment


class AuthActivity : AppCompatActivity() {

    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var auth = intent?.getIntExtra("auth", 0)

        if(auth == 1) {
            val loginFragment = LoginFragment()

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container_auth, loginFragment)
            transaction.commit()
        } else if(auth == 0) {
            val signUpFragment = SignUpFragment()

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container_auth, signUpFragment)
            transaction.commit()
        } else {
            val addressFragment = SignUpAddressFragment()

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container_auth, addressFragment)
            transaction.commit()
        }
    }
}