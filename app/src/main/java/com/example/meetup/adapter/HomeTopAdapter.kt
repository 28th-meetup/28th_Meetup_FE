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
import com.example.meetup.databinding.RowTop10Binding

class HomeTopAdapter() :
    RecyclerView.Adapter<HomeTopAdapter.HomeTopViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTopViewHolder {
        context = parent.context
        val binding =
            RowTop10Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeTopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeTopViewHolder, position: Int) {
        holder.rank.text = "${position+1}"
        holder.image.setImageResource(R.drawable.food)
        holder.storeName.text = "중국집"
        holder.foodName.text = "짬뽕"
        holder.price.text = "10,000원"
    }

    override fun getItemCount() = 10

    inner class HomeTopViewHolder(val binding: RowTop10Binding) : RecyclerView.ViewHolder(binding.root) {
        val rank = binding.textviewRank
        val image = binding.imageview
        val storeName = binding.textviewStoreName
        val foodName = binding.textviewFoodName
        val price = binding.textviewPrice

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
}