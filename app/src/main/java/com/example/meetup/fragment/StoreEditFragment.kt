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
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentMenuAddBinding
import com.example.meetup.databinding.FragmentMenuEditBinding
import com.example.meetup.databinding.FragmentStoreEditBinding


class StoreEditFragment : Fragment() {


    var imageNum = 0

    var isCheck = false
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

        var loactionSpinner = binding.spinnerLocation	// spinner
        var locationArray = resources.getStringArray(R.array.location_array)	// 배열
        setSpinner(loactionSpinner, locationArray)	// 스피너 설정
        var phoneSpinner = binding.spinnerPhoneNumber	// spinner
        var phoneArray = resources.getStringArray(R.array.phone_array)	// 배열
        setSpinner(phoneSpinner, phoneArray)	// 스피너 설정
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

                    imageNum = uris.size
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

            if(binding.edittextStoreName.text.toString()=="") {
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if(imageNum != 3) {
                Toast.makeText(context, "사진 3개 선택해주세요.", Toast.LENGTH_SHORT).show()

            } else if(binding.edittextMinimumPrice.text.toString() == "") {
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if(binding.edittextStoreExplain.text.toString() ==""){
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if (binding.edittextPhoneNumber.text.toString()==""){
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if(binding.edittextLocation1.text.toString()==""){
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if(binding.edittextLocation2.text.toString()==""){
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if(binding.edittextDeliverAbleArea.text.toString()==""){
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if(binding.edittextRunTime.text.toString()==""){
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if(!isCheck){
                Toast.makeText(context, "체크 표시 해주세요.", Toast.LENGTH_SHORT).show()

            } else {

                val storeAddSuccessFragment =StoreAddSuccessFragment()
                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.frameArea, storeAddSuccessFragment)
                    commit()
                }

            }

        }

    }

    fun imageviewCheckYes() {
        binding.imageviewCheckYes.setImageResource(R.drawable.ic_check_square_check)
        binding.imageviewCheckNo.setImageResource(R.drawable.ic_checkbox_uncheck)
        binding.btnSaveColor.setBackgroundColor(Color.parseColor("#E60051"))
        isCheck = true
    }

    fun imageviewCheckNo() {
        binding.imageviewCheckYes.setImageResource(R.drawable.ic_checkbox_uncheck)
        binding.imageviewCheckNo.setImageResource(R.drawable.ic_check_square_check)
        binding.btnSaveColor.setBackgroundColor(Color.parseColor("#E60051"))
        isCheck = true

    }
    private fun setSpinner(spinner: Spinner, array: Array<String>) {
        var adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line
        ) { override fun getCount(): Int =  super.getCount() }  // array에서 hint 안 보이게 하기
        adapter.addAll(array.toMutableList())   // 배열 추가
        spinner.adapter = adapter               // 어댑터 달기
//        spinner.setSelection(, false)    // 스피너 초기값=hint
    }
}