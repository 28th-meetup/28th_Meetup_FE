package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.model.chatting.ChatListResult
import com.example.meetup.model.chatting.ChattingListResponseModel

class ChattingListAdapter(
     var chattingList: ArrayList<ChatListResult>
) :
    RecyclerView.Adapter<ChattingListAdapter.ViewHolder>() {

//    var filtered = ArrayList<ChattingListResponseModel>()
//    var itemFilter = ItemFilter()

//    init {
//        filtered.addAll(chattingList)
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChattingListAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_chatting_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChattingListAdapter.ViewHolder, position: Int) {
        val item = chattingList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return chattingList.size
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)

    }

    var itemClick: ItemClick? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageview_chatting_image: ImageView = view.findViewById(R.id.imageview_chatting_image)
        var textview_chatting_name: TextView = view.findViewById(R.id.textview_chatting_name)
        var textview_chatting_content: TextView = view.findViewById(R.id.textview_chatting_content)
        var textview_chatting_time: TextView = view.findViewById(R.id.textview_chatting_time)
        var textview_chatting_num: TextView = view.findViewById(R.id.textview_chatting_num)

        fun bind(item: ChatListResult) {

            textview_chatting_name.text = item.sender
            textview_chatting_content.text = item.message
//            textview_chatting_time.text = item.
//            textview_chatting_num.text = item.

            itemView.setOnClickListener {

                itemClick?.onClick(itemView, adapterPosition)

            }

        }
    }

//    override fun getFilter(): Filter {
//
//        return itemFilter
//    }

//    inner class ItemFilter : Filter() {
//        override fun performFiltering(p0: CharSequence?): FilterResults {
//            val filterString = p0.toString()
//            val results = FilterResults()
//            //검색이 필요없을 경우를 위해 원본 배열을 복제
//
//            val filterList: ArrayList<ChattingListResponseModel> =
//                ArrayList<ChattingListResponseModel>()
//            //공백제외 아무런 값이 없을 경우 -> 원본 배열
//
//            if (filterString.trim { it <= ' ' }.isEmpty()) {
//                results.values = chattingList
//                results.count = chattingList.size
//
//                return results
//            } else {
//                for (a in chattingList) {
//                    if (a.textview_chatting_name.contains(filterString)) {
//                        filterList.add(a)
//                    }
//                }
//            }
//
//            results.values = filterList
//            results.count = filterList.size
//
//            return results
//        }

//        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
//            filtered.clear()
//            filtered.addAll(p1?.values as ArrayList<ChattingListResponseModel>)
//            notifyDataSetChanged()
//        }

//    }

}