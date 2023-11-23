package com.example.meetup.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.databinding.RowCategoryBinding
import com.example.meetup.databinding.RowTop10Binding
import com.example.meetup.fragment.MenuFragment
import com.example.meetup.model.BestSellingFoodList
import com.example.meetup.sharedPreference.MyApplication
import com.example.meetup.viewmodel.FoodMenuDetailViewModel

class HomeTopAdapter(var manager: FragmentManager, var activity:ViewModelStoreOwner, var foodList: List<BestSellingFoodList>) :
    RecyclerView.Adapter<HomeTopAdapter.HomeTopViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    lateinit var viewModel: FoodMenuDetailViewModel

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTopViewHolder {
        context = parent.context
        val binding =
            RowTop10Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel = ViewModelProvider(activity)[FoodMenuDetailViewModel::class.java]

        return HomeTopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeTopViewHolder, position: Int) {
        holder.rank.text = "${position+1}"
        Glide.with(context!!).load(foodList.get(position).image).into(holder.image)
        holder.storeName.text = "${foodList.get(position).storeName}"
        holder.foodName.text = "${foodList.get(position).name}"
        if(foodList.get(position).dollarPrice.toInt() == 0) {
            holder.price.text = "${foodList.get(position).canadaPrice}"
        }
        else {
            holder.price.text = "$ ${foodList.get(position).dollarPrice}"
        }
    }

    override fun getItemCount() = foodList.size

    inner class HomeTopViewHolder(val binding: RowTop10Binding) : RecyclerView.ViewHolder(binding.root) {
        val rank = binding.textviewRank
        val image = binding.imageview
        val storeName = binding.textviewStoreName
        val foodName = binding.textviewFoodName
        val price = binding.textviewPrice

        init {
            binding.root.setOnClickListener {
                MyApplication.foodId = foodList.get(adapterPosition).foodId.toInt()

                viewModel.getFoodMenuInfo(context!!, manager, MyApplication.foodId)

            }
        }
    }
}