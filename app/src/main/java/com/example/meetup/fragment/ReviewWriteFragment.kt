package com.example.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentReviewWriteBinding


class ReviewWriteFragment : BaseFragment<FragmentReviewWriteBinding>(R.layout.fragment_review_write) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChoosePhoto.setOnClickListener{
            //photopicker 사용
        }
        binding.imageviewStar1.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star_gray)


        }
        binding.imageviewStar2.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star_gray)

        }
        binding.imageviewStar3.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star_gray)

        }
        binding.imageviewStar4.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star_gray)

        }
        binding.imageviewStar5.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star)

        }
    }


}