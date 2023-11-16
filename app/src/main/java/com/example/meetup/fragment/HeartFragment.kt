package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.FragmentChattingBinding
import com.example.meetup.databinding.FragmentHeartBinding


class HeartFragment : Fragment() {
    private var _binding: FragmentHeartBinding? = null
    private val binding get() = _binding!!

    lateinit var homeActivity: HomeActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeActivity = activity as HomeActivity

        homeActivity.hideBottomNavigation(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentHeartBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.imageviewAlarm.setOnClickListener {
            val alarmFragment = AlarmFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, alarmFragment)
                addToBackStack(null)
                commit()
            }
        }
        binding.imageviewCart.setOnClickListener {
            val cartFragment = CartFragment()
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frameArea, cartFragment)
                addToBackStack(null)
                commit()
            }
        }

        return view
    }


}