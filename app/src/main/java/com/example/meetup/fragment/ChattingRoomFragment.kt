package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.databinding.FragmentChattingBinding
import com.example.meetup.databinding.FragmentChattingRoomBinding


class ChattingRoomFragment : Fragment() {
    private var _binding: FragmentChattingRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChattingRoomBinding.inflate(inflater, container, false)
        val view = binding.root





        return view
    }


}