package com.example.meetup.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.meetup.R
import com.example.meetup.databinding.ActivityHomeBinding
import com.example.meetup.databinding.FragmentHomeBinding
import com.example.meetup.fragment.HeartFragment
import com.example.meetup.fragment.HomeFragment
import com.example.meetup.fragment.MyPageFragment
import com.example.meetup.fragment.MyPageSellerFragment
import com.example.meetup.fragment.StoreFragment
import com.example.meetup.sharedPreference.MyApplication
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    val manager = supportFragmentManager
    lateinit var binding: ActivityHomeBinding


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)


        setContentView(binding.root)


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val itemColor = ContextCompat.getColorStateList(this, R.drawable.bottom_navigation_click)


        val transaction = manager.beginTransaction()

        val homeFragment = HomeFragment()
        val storeFragment = StoreFragment()
        val heartFragment = HeartFragment()
        val myPageFragment = MyPageFragment()
        val sellerMyPageFragment = MyPageSellerFragment()

        transaction.replace(R.id.frameArea, homeFragment)
        transaction.addToBackStack(null)
        transaction.commit()

        bottomNav.itemIconTintList = itemColor
        bottomNav.itemTextColor = itemColor


        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab1 -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.frameArea, homeFragment)
                    transaction.commit()
                    true
                }

                R.id.tab2 -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.frameArea, storeFragment)
                    transaction.commit()
                    true
                }

                R.id.tab3 -> {
                    //로그인 하고 안하고 조건문 만들기
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.frameArea, heartFragment)
                    transaction.commit()
                    true
                }

                R.id.tab4 -> {
                    Log.d("밋업", "mypage : ${MyApplication.mypageSeller}")
                    if(MyApplication.mypageSeller) {
                        val transaction = manager.beginTransaction()
                        transaction.replace(R.id.frameArea, sellerMyPageFragment)
                        transaction.commit()
                    } else {
                        val transaction = manager.beginTransaction()
                        transaction.replace(R.id.frameArea, myPageFragment)
                        transaction.commit()
                    }
                    true
                }

                else -> false
            }
        }
    }

    fun hideBottomNavigation(state:Boolean){
        if(state) binding.bottomNav.visibility = View.GONE else binding.bottomNav.visibility=View.VISIBLE
    }
}