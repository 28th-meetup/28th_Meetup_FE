package com.example.meetup.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.FragmentCertificationCompleteBinding

class CertificationCompleteFragment : Fragment() {

    lateinit var binding: FragmentCertificationCompleteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCertificationCompleteBinding.inflate(inflater)

        binding.run {
            buttonHome.setOnClickListener {
            }
        }
        return binding.root
    }
}