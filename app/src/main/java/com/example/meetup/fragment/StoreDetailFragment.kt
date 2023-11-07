package com.example.meetup.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetup.R
import com.example.meetup.databinding.FragmentStoreBinding
import com.example.meetup.databinding.FragmentStoreDetailBinding

class StoreDetailFragment : Fragment() {
    private var _binding: FragmentStoreDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoreDetailBinding.inflate(inflater,container,false)
        val view = binding.root

        //초기 설정
        val storeDetailMenuFragment = StoreDetailMenuFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.store_detail_frame_area, storeDetailMenuFragment)
            addToBackStack(null)
            commit()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //메뉴 클릭
        binding.btnMenu.setOnClickListener {

            btnMenuClick()
        }

        //가게 정보 클릭
        binding.btnStoreInfo.setOnClickListener {

            btnStoreInfoClick()
        }

        //공지사항 클릭
        binding.btnNotice.setOnClickListener {

            btnNoticeClick()
        }

        //리뷰 클릭
        binding.btnReview.setOnClickListener {

            btnReviewClick()
        }

        binding.btnBack.setOnClickListener {
            btnBackClick()
        }
    }

    fun btnBackClick() {
        val storeFragment = StoreFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.frameArea, storeFragment)
            addToBackStack(null)
            commit()
        }
    }
    fun btnMenuClick() {

        binding.btnMenu.setTextColor(Color.parseColor("#000000"))
        binding.btnStoreInfo.setTextColor(Color.parseColor("#6E7178"))
        binding.btnNotice.setTextColor(Color.parseColor("#6E7178"))
        binding.btnReview.setTextColor(Color.parseColor("#6E7178"))

        binding.btnMenu.setBackgroundResource(R.drawable.store_select_border)
        binding.btnStoreInfo.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnNotice.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnReview.setBackgroundResource(R.drawable.store_not_select_border)


        val storeDetailMenuFragment = StoreDetailMenuFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.store_detail_frame_area, storeDetailMenuFragment)
            addToBackStack(null)
            commit()
        }

    }
    fun btnStoreInfoClick() {

        binding.btnMenu.setTextColor(Color.parseColor("#6E7178"))
        binding.btnStoreInfo.setTextColor(Color.parseColor("#000000"))
        binding.btnNotice.setTextColor(Color.parseColor("#6E7178"))
        binding.btnReview.setTextColor(Color.parseColor("#6E7178"))

        binding.btnMenu.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnStoreInfo.setBackgroundResource(R.drawable.store_select_border)
        binding.btnNotice.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnReview.setBackgroundResource(R.drawable.store_not_select_border)

        val storeDetailInfoFragment = StoreDetailInfoFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.store_detail_frame_area, storeDetailInfoFragment)
            addToBackStack(null)
            commit()
        }

    }

    fun btnNoticeClick() {


        binding.btnMenu.setTextColor(Color.parseColor("#6E7178"))
        binding.btnStoreInfo.setTextColor(Color.parseColor("#6E7178"))
        binding.btnNotice.setTextColor(Color.parseColor("#000000"))
        binding.btnReview.setTextColor(Color.parseColor("#6E7178"))

        binding.btnMenu.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnStoreInfo.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnNotice.setBackgroundResource(R.drawable.store_select_border)
        binding.btnReview.setBackgroundResource(R.drawable.store_not_select_border)

        val storeDetailNoticeFragment = StoreDetailNoticeFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.store_detail_frame_area, storeDetailNoticeFragment)
            addToBackStack(null)
            commit()
        }

    }

    fun btnReviewClick() {

        binding.btnMenu.setTextColor(Color.parseColor("#6E7178"))
        binding.btnStoreInfo.setTextColor(Color.parseColor("#6E7178"))
        binding.btnNotice.setTextColor(Color.parseColor("#6E7178"))
        binding.btnReview.setTextColor(Color.parseColor("#000000"))

        binding.btnMenu.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnStoreInfo.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnNotice.setBackgroundResource(R.drawable.store_not_select_border)
        binding.btnReview.setBackgroundResource(R.drawable.store_select_border)

        val storeDetailReviewFragment = StoreDetailReviewFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.store_detail_frame_area, storeDetailReviewFragment)
            addToBackStack(null)
            commit()
        }
    }


}