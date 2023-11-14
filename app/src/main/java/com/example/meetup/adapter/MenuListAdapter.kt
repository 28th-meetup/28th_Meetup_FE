package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.model.MenuListResponseModel


class MenuListAdapter(
    private var menuList: ArrayList<MenuListResponseModel>,
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


        fun bind(item: MenuListResponseModel) {

            textview_menu_name.text = item.menuName
            textview_menu_category.text = item.menuCategory
            textview_menu_price.text = item.menuPrice

        }
    }
}