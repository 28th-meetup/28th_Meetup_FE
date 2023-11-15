package com.example.meetup.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentMenuAddBinding
import com.example.meetup.databinding.FragmentMenuEditBinding
import com.example.meetup.databinding.FragmentStoreEditBinding


class StoreEditFragment : Fragment() {


    private var _binding: FragmentStoreEditBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoreEditBinding.inflate(inflater,container,false)
        val view = binding.root


        val spinnerMoney = resources.getStringArray(R.array.spinner_money)
        var moneyAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, spinnerMoney)

        moneyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerMoney.adapter = moneyAdapter
        binding.spinnerMoney.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        return view



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uris != null) {
                    Log.d("PhotoPicker", "Selected URI: $uris")

//                    bitmap = BitmapFactory.decodeStream(
//                        requireContext().contentResolver.openInputStream(uri)
//                    )

//
//
//                    Glide.with(this)
//                        .load(uri)
//                        .into(binding.imageviewChooseRepresent)

                    when(uris.size) {
                        1-> {
                            binding.imageviewPhoto1.visibility = View.VISIBLE
                            binding.imageviewPhoto2.visibility = View.GONE
                            binding.imageviewPhoto3.visibility = View.GONE

                            Glide.with(this)
                                .load(uris.get(0))
                                .into(binding.imageviewPhoto1)
//
//                            Glide.with(this)
//                                .load("")
//                                .into(binding.imageviewPhoto2)
//
//                            Glide.with(this)
//                                .load("")
//                                .into(binding.imageviewPhoto3)

                        }
                        2-> {
                            binding.imageviewPhoto1.visibility = View.VISIBLE
                            binding.imageviewPhoto2.visibility = View.VISIBLE
                            binding.imageviewPhoto3.visibility = View.GONE

                            Glide.with(this)
                                .load(uris.get(0))
                                .into(binding.imageviewPhoto1)
                            Glide.with(this)
                                .load(uris.get(1))
                                .into(binding.imageviewPhoto2)
//                            Glide.with(this)
//                                .load("")
//                                .into(binding.imageviewPhoto3)

                        }
                        3-> {
                            binding.imageviewPhoto1.visibility = View.VISIBLE
                            binding.imageviewPhoto2.visibility = View.VISIBLE
                            binding.imageviewPhoto3.visibility = View.VISIBLE

                            Glide.with(this)
                                .load(uris.get(0))
                                .into(binding.imageviewPhoto1)
                            Glide.with(this)
                                .load(uris.get(1))
                                .into(binding.imageviewPhoto2)
                            Glide.with(this)
                                .load(uris.get(2))
                                .into(binding.imageviewPhoto3)
                        }
                    }

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

        //저장하기
        binding.btnSaveColor.setOnClickListener {

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