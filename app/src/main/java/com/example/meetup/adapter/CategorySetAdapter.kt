package com.example.meetup.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.databinding.RowSetBinding
import com.example.meetup.fragment.MenuFragment
import com.example.meetup.model.Food
import com.example.meetup.sharedPreference.MyApplication

class CategorySetAdapter(var manager: FragmentManager, var foodList: List<Food>) :
    RecyclerView.Adapter<CategorySetAdapter.CategorySetViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySetViewHolder {
        context = parent.context
        val binding =
            RowSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return CategorySetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategorySetViewHolder, position: Int) {
        Glide.with(context!!).load(foodList.get(position).image).into(holder.image)
        holder.storeName.text = "${foodList.get(position).storeId}"
        holder.review.text = "⭐️ 4.8"
        holder.foodName.text = "${foodList.get(position).name}"
        holder.price.text = "${foodList.get(position).dollarPrice}"
    }

    override fun getItemCount() = foodList.size

    inner class CategorySetViewHolder(val binding: RowSetBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageview
        val storeName = binding.textviewStoreName
        val review = binding.textviewReview
        val foodName = binding.textviewFoodName
        val price = binding.textviewPrice

        init {
            binding.root.setOnClickListener {

                MyApplication.foodId = foodList.get(adapterPosition).id.toInt()

                val menuFragment = MenuFragment()

                val transaction = manager.beginTransaction()
                transaction.replace(R.id.frameArea, menuFragment)
                transaction.commit()
                true
            }
        }
    }

    internal class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
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