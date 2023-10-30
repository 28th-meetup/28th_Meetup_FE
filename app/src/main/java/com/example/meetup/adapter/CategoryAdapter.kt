package com.example.meetup.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.databinding.RowCategoryBinding

class CategoryAdapter(var categoryNameList: Array<String>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        context = parent.context
        val binding =
            RowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.image.setImageResource(R.drawable.ic_category1)
        holder.name.text = categoryNameList.get(position)
    }

    override fun getItemCount() = categoryNameList.size

    inner class CategoryViewHolder(val binding: RowCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageviewRowCategory
        val name = binding.textviewRowCategory

        init {
            binding.root.setOnClickListener {
//                val context = it.context
//                val intent = Intent(context, PostActivity::class.java)
//                intent.putExtra("제목", binding.tvTitle.text)
//                intent.putExtra("내용", binding.tvContent.text)
//                intent.putExtra("id", articleId)
//                ContextCompat.startActivity(context, intent, null)
            }
        }
    }

    internal class GridSpaceItemDecoration(private val spanCount: Int, private val space: Int): RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount + 1      // 1부터 시작

            /** 첫번째 행(row-1)에 있는 아이템인 경우 상단에 [space] 만큼의 여백을 추가한다 */
            if (position < spanCount){
                outRect.top = space
            }
            /** 마지막 열(column-N)에 있는 아이템이 아닌 경우 우측에 [space] 만큼의 여백을 추가한다 */
            if (column != spanCount) {
                outRect.right = space
            }
            /** 모든 아이템의 좌측과 하단에 [space] 만큼의 여백을 추가한다. */
            outRect.left = space
            outRect.bottom = space

        }
    }
}