package com.example.meetup.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.activity.MainActivity
import com.example.meetup.databinding.RowCategoryBinding
import com.example.meetup.fragment.HomeCategoryFragment
import com.example.meetup.fragment.HomeFragment
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.CategoryFoodViewModel
import kotlin.math.acos

class CategoryAdapter(var categoryNameList: Array<String>, var manager: FragmentManager, var activity: ViewModelStoreOwner) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    lateinit var categoryViewModel: CategoryFoodViewModel
    lateinit var homeActivity: HomeActivity

    var categoryImageList = listOf<Int>(
        R.drawable.ic_category0,
        R.drawable.ic_category1,
        R.drawable.ic_category2,
        R.drawable.ic_category3,
        R.drawable.ic_category4,
        R.drawable.ic_category5,
        R.drawable.ic_category6,
        R.drawable.ic_category7,
        R.drawable.ic_category8,
        R.drawable.ic_category9
    )

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        context = parent.context
        val binding =
            RowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        categoryViewModel = ViewModelProvider(activity)[CategoryFoodViewModel::class.java]

        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.image.setImageResource(categoryImageList.get(position))
        holder.name.text = categoryNameList.get(position)
    }

    override fun getItemCount() = categoryNameList.size

    inner class CategoryViewHolder(val binding: RowCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageviewRowCategory
        val name = binding.textviewRowCategory

        init {
            binding.root.setOnClickListener {

                categoryViewModel.getCategoryFoodInfo(context!!, "RECOMMENDED", adapterPosition+1)

                val homeCategoryFragment = HomeCategoryFragment()

                MyApplication.categoryId = adapterPosition+1
                MyApplication.category = categoryNameList.get(adapterPosition)

                val transaction = manager.beginTransaction()
                transaction.replace(R.id.frameArea, homeCategoryFragment)
                transaction.commit()
                true
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