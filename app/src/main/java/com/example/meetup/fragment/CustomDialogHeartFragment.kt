package com.example.meetup.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.meetup.R

class CustomDialogHeartFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_dialog_heart, null)


        val btnX = view.findViewById<ImageView>(R.id.btn_x)
//        val btnShowHeartList = view.findViewById<TextView>(R.id.btn_show_heart_list)

        builder.setView(view)

        btnX.setOnClickListener {
            // 예: 텍스트를 가져와 다른 작업 수행 또는 다이얼로그 닫기
            dismiss()
        }

        return builder.create()
    }
}