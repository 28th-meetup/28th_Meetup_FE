package com.example.meetup.fragment

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meetup.R
import com.example.meetup.adapter.HomeSetAdapter
import com.example.meetup.databinding.FragmentHomeCategoryBinding
import com.example.meetup.Util.fromDpToPx
import com.example.meetup.activity.HomeActivity
import com.example.meetup.sharedPreference.MyApplication

class HomeCategoryFragment : Fragment() {

    lateinit var binding: FragmentHomeCategoryBinding
    lateinit var homeActivity: HomeActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeCategoryBinding.inflate(inflater)
        homeActivity = activity as HomeActivity

        initView()

        binding.run {
            toolbar.run {
                title = "${MyApplication.category}"

                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_back)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    val homeFragment = HomeFragment()

                    val transaction = homeActivity.manager.beginTransaction()
                    transaction.replace(R.id.frameArea, homeFragment)
                    transaction.commit()
                }

                inflateMenu(R.menu.main_cart_menu)

                setOnMenuItemClickListener {
                    when (it.itemId) {

                        R.id.item_main_cart -> {

                        }

                        else -> { }
                    }
                    true
                }
            }
        }
        return binding.root
    }

    fun initView() {
        binding.run {
            homeActivity.hideBottomNavigation(true)

            recyclerview.run {
                adapter = HomeSetAdapter()

                layoutManager = GridLayoutManager(requireContext(), 2)

                val spanCount = 2
                val space = 22.83f.fromDpToPx()
                addItemDecoration(HomeSetAdapter.GridSpacingItemDecoration(spanCount, space, false))
            }
        }
    }
}