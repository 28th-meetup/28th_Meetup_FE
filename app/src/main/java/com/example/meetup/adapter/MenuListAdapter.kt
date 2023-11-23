package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.model.MenuListResponseModel
import com.example.meetup.model.store.GetStoreMenuResponseModelResult


class MenuListAdapter(
    private var menuList: ArrayList<GetStoreMenuResponseModelResult>,
    private val itemClickListener1: (Int) -> Unit,
    private val itemClickListener2: (Int) -> Unit,

    ) : RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_menu_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuListAdapter.ViewHolder, position: Int) {
        val item = menuList[position]
        holder.bind(item)


        //클릭 이벤트
        holder.btn_edit.setOnClickListener {

            itemClickListener1.invoke(position)
        }

        holder.btn_delete.setOnClickListener {
            itemClickListener2.invoke(position)

        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageview_menu_image: ImageView = view.findViewById(R.id.imageview_menu_image)
        var textview_menu_name: TextView = view.findViewById(R.id.textview_menu_name)
        var textview_menu_category: TextView = view.findViewById(R.id.textview_menu_category)
        var textview_menu_price: TextView = view.findViewById(R.id.textview_menu_price)

        var btn_edit : ImageView = view.findViewById(R.id.btn_edit)
        var btn_delete : ImageView = view.findViewById(R.id.btn_delete)



        fun bind(item: GetStoreMenuResponseModelResult) {

            var a = ""

            if(item.categoryId ==1L){
                a = "카테고리 : 밑반찬"
            } else if(item.categoryId ==2L){
                a = "카테고리 : 김치"
            }else if(item.categoryId ==3L){
                a = "카테고리 : 국물류"
            }else if(item.categoryId ==4L){
                a = "카테고리 : 면류"
            }else if(item.categoryId ==5L){
                a = "카테고리 : 고기류"
            }else if(item.categoryId ==6L){
                a = "카테고리 : 해물류"
            }else if(item.categoryId ==7L){
                a = "카테고리 : 밥/죽"
            }else if(item.categoryId ==8L){
                a = "카테고리 : 간식/음료"
            }else if(item.categoryId ==9L){
                a = "카테고리 : 반조리"
            }else if(item.categoryId ==10L){
                a = "카테고리 : 기타"
            }
            Glide.with(itemView.context)
                .load(item.image)
                .into(imageview_menu_image)
            textview_menu_name.text = item.name
            textview_menu_category.text = a
            textview_menu_price.text = item.dollarPrice.toString()

        }
    }
}