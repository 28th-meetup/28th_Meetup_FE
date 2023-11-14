package com.example.meetup.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentStoreEditBinding


class StoreEditFragment : BaseFragment<FragmentStoreEditBinding>(R.layout.fragment_store_edit) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")

//                    bitmap = BitmapFactory.decodeStream(
//                        requireContext().contentResolver.openInputStream(uri)
//                    )

                    Glide.with(this)
                        .load(uri)
                        .into(binding.imageviewChooseRepresent)



                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        //뒤로가기
        binding.btnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        //대표 이미지 클릭
        binding.imageviewChooseRepresent.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }

        //예 클릭
        binding.imageviewCheckYes.setOnClickListener {
            imageviewCheckYes()
        }

        //아니요 클릭
        binding.imageviewCheckNo.setOnClickListener {
            imageviewCheckNo()
        }
    }

    fun imageviewCheckYes() {
        binding.imageviewCheckYes.setImageResource(R.drawable.ic_check_square_check)
        binding.imageviewCheckNo.setImageResource(R.drawable.ic_checkbox_uncheck)
        binding.btnSaveColor.setBackgroundColor(Color.parseColor("#E60051"))
    }

    fun imageviewCheckNo() {
        binding.imageviewCheckYes.setImageResource(R.drawable.ic_checkbox_uncheck)
        binding.imageviewCheckNo.setImageResource(R.drawable.ic_check_square_check)
        binding.btnSaveColor.setBackgroundColor(Color.parseColor("#E60051"))
    }

}