package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.model.GetStoreDetailReviewResponseModel
import com.example.meetup.model.GetStoreDetailReviewResponseModelResult
import com.example.meetup.model.ReviewList
import com.example.meetup.model.review.ReviewListResponseModel

class ReviewListAdapter (private var reviewList : ArrayList<ReviewList>) :
    RecyclerView.Adapter<ReviewListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewListAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_review,parent,false)
        return ViewHolder(view)

    }

    interface ItemClick {
        fun onClick(view: View, position: Int)

    }

    var itemClick : ItemClick? = null
    override fun onBindViewHolder(holder: ReviewListAdapter.ViewHolder, position: Int) {
        val item = reviewList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
            return reviewList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var textview_option_menu : TextView = view.findViewById(R.id.textview_option_menu)
        var imageview_review_image : ImageView = view.findViewById(R.id.imageview_review_image)
        var textview_review_content : TextView = view.findViewById(R.id.textview_review_content)
        var textview_review_writer : TextView = view.findViewById(R.id.textview_review_writer)
        var textview_review_date : TextView = view.findViewById(R.id.textview_review_date)
        fun bind(item: ReviewList) {

            Glide.with(itemView.context)
                .load(item.image)
                .into(imageview_review_image)
            textview_option_menu.text = item.orderId.toString()
            textview_review_content.text = item.message
            textview_review_writer.text = item.id.toString()
//            textview_review_date.text = item.


            itemView.setOnClickListener {

                itemClick?.onClick(itemView,adapterPosition)

            }

        }
    }
}