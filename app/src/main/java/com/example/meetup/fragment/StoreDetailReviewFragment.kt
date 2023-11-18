package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.adapter.ReviewListAdapter
import com.example.meetup.databinding.FragmentStoreDetailReviewBinding
import com.example.meetup.model.review.ReviewListResponseModel


class StoreDetailReviewFragment : Fragment() {

    private var _binding: FragmentStoreDetailReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var reviewListAdapter: ReviewListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoreDetailReviewBinding.inflate(inflater,container,false)
        val view = binding.root

        val review_list = ArrayList<ReviewListResponseModel>()

        review_list.add(ReviewListResponseModel("aa","aa","aa","aa","Aa","2020-02-02"))
        review_list.add(ReviewListResponseModel("bb","bb","bb","bb","bb","2020-01-02"))
        review_list.add(ReviewListResponseModel("cc","cc","cc","cc","cc","2020-01-01"))


        reviewListAdapter = ReviewListAdapter(review_list)

        binding.recyclerviewStoreDetailReview.adapter = reviewListAdapter

        binding.recyclerviewStoreDetailReview.layoutManager = LinearLayoutManager(context)
        return view
    }


}