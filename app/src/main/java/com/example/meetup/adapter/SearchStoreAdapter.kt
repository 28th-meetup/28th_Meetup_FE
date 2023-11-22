package com.example.meetup.adapter

import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.databinding.RowSearchStoreBinding
import com.example.meetup.databinding.RowSetBinding
import com.example.meetup.fragment.MenuFragment
import com.example.meetup.fragment.OrderCompleteFragment
import com.example.meetup.fragment.StoreDetailFragment
import com.example.meetup.model.SearchFood
import com.example.meetup.model.StoreDto
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.FoodMenuDetailViewModel
import com.example.meetup.viewmodel.StoreListViewModel

class SearchStoreAdapter(var manager: FragmentManager, var activity: ViewModelStoreOwner, var storeList: List<StoreDto>) :
    RecyclerView.Adapter<SearchStoreAdapter.SearchViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    lateinit var viewModel: StoreListViewModel

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        context = parent.context
        val binding =
            RowSearchStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        viewModel = ViewModelProvider(activity)[StoreListViewModel::class.java]

        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        Glide.with(context!!).load(storeList.get(position).images.get(0)).into(holder.storeImage)
        holder.storeName.text = "${storeList.get(position).name}"
        holder.review.text = "⭐️ ${storeList.get(position).avgRate}"
        if(storeList.get(position).koreanYn) {
            holder.badge.visibility = View.VISIBLE
        } else {
            holder.badge.visibility = View.GONE
        }
        holder.minOrderAmount.text = "${storeList.get(position).minOrderAmount}"
    }

    override fun getItemCount() = storeList.size

    inner class SearchViewHolder(val binding: RowSearchStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val storeImage = binding.imageviewStoreMainImage
        val storeName = binding.textviewStoreName
        val review = binding.textviewRate
        val minOrderAmount = binding.textviewMinOrderAmount
        val badge = binding.imageviewBadge

        init {
            binding.root.setOnClickListener {
                // 가게 상세 정보 화면으로 이동
                Handler().postDelayed({
                    viewModel.getStoreDetail(context!!, storeList.get(adapterPosition).id)

                    val storeFragment = StoreDetailFragment()

                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.frameArea, storeFragment)
                    transaction.commit()
                }, 1000)
                true
            }
        }
    }

    internal class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // 아이템의 위치를 가져옴
            val column = position % spanCount // 아이템이 속한 열을 계산

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
            }

            if (position < spanCount) {
                outRect.top = spacing // 첫 번째 행에는 위쪽 간격을 설정
            }
            outRect.bottom = spacing // 아래쪽 간격을 설정
        }
    }
}