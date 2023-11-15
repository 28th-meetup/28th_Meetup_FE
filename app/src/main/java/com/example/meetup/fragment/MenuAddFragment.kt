package com.example.meetup.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.Color
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentMenuAddBinding
import com.example.meetup.databinding.FragmentStoreDetailBinding


class MenuAddFragment : Fragment() {

    private var _binding: FragmentMenuAddBinding? = null
    private val binding get() = _binding!!


    var menuName = ""
    var menuCategory = ""
    var menuPrice = 0L
    var menuImageUrl = ""
    var menuContext = ""
    var deliverTogo = ""
    var optionNum = 1

    var ischeck = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuAddBinding.inflate(inflater, container, false)
        val view = binding.root


        val optionData = resources.getStringArray(R.array.spinner_option)
        var adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, optionData)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.textviewAddOption.adapter = adapter

        binding.textviewAddOption.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        val spinnerMoney = resources.getStringArray(R.array.spinner_money)
        var moneyAdapter =
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                spinnerMoney
            )

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

        //메뉴 카테고리 선택 클릭
        binding.textviewMenuCategory.setOnClickListener {
            menuCategoryClick()
        }

        //대표 메뉴 이미지 클릭
        binding.imageviewChooseRepresent.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }

        //옵션 추가 클릭
        binding.btnOptionAdd.setOnClickListener {

            btnOptionAdd()
        }

        binding.cardviewAdd.setOnClickListener {
            cardviewAdd()
        }
        binding.btnX1.setOnClickListener {
            binding.cardviewOption1.visibility = View.GONE
            optionNum--
        }
        binding.btnX2.setOnClickListener {
            binding.cardviewOption2.visibility = View.GONE

            optionNum--
        }
        binding.btnX3.setOnClickListener {
            binding.cardviewOption3.visibility = View.GONE
            optionNum--

        }
        binding.btnX4.setOnClickListener {
            binding.cardviewOption4.visibility = View.GONE
            optionNum--

        }
        binding.btnX5.setOnClickListener {
            binding.cardviewOption5.visibility = View.GONE
            optionNum--

        }

        //배달 포장 모두 가능 클릭
        binding.btnOptionAllAble.setOnClickListener {
            btnOptionAllAbleClick()
        }

        //배달만 가능 클릭
        binding.btnOptionOnlyDelivery.setOnClickListener {
            btnOptionOnlyDelivery()
        }

        //포장만 가능 클릭
        binding.btnOptionOnlyTogo.setOnClickListener {
            btnOptionOnlyTogo()
        }

        //뒤로 가기 클릭
        binding.btnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    fun btnOptionAdd() {
        when (optionNum) {
            0 -> {
                binding.cardviewOption1.visibility = View.VISIBLE
                optionNum++
            }

            1 -> {
                binding.cardviewOption2.visibility = View.VISIBLE
                optionNum++
            }

            2 -> {
                binding.cardviewOption3.visibility = View.VISIBLE
                optionNum++
            }

            3 -> {
                binding.cardviewOption4.visibility = View.VISIBLE
                optionNum++
            }

            4 -> {
                binding.cardviewOption5.visibility = View.VISIBLE
                optionNum++
            }

        }
    }

    fun menuCategoryClick() {
        if (binding.linearLayoutCategory.visibility == View.GONE) {
            binding.linearLayoutCategory.visibility = View.VISIBLE

        } else if (binding.linearLayoutCategory.visibility == View.VISIBLE) {
            binding.linearLayoutCategory.visibility = View.GONE
        }


        binding.textviewCaterogy1.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy1.text
            binding.linearLayoutCategory.visibility = View.GONE
        }

        binding.textviewCaterogy2.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy2.text
            binding.linearLayoutCategory.visibility = View.GONE
        }
        binding.textviewCaterogy3.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy3.text
            binding.linearLayoutCategory.visibility = View.GONE
        }
        binding.textviewCaterogy4.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy4.text
            binding.linearLayoutCategory.visibility = View.GONE
        }
        binding.textviewCaterogy5.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy5.text
            binding.linearLayoutCategory.visibility = View.GONE
        }
        binding.textviewCaterogy6.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy7.text
            binding.linearLayoutCategory.visibility = View.GONE
        }
        binding.textviewCaterogy7.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy8.text
            binding.linearLayoutCategory.visibility = View.GONE
        }
        binding.textviewCaterogy8.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy8.text
            binding.linearLayoutCategory.visibility = View.GONE
        }
        binding.textviewCaterogy9.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy9.text
            binding.linearLayoutCategory.visibility = View.GONE
        }
        binding.textviewCaterogy10.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy10.text
            binding.linearLayoutCategory.visibility = View.GONE
        }

    }

    fun btnOptionAllAbleClick() {
        binding.btnOptionAllAble.strokeColor = android.graphics.Color.parseColor("#E60051")
        binding.btnOptionAllAbleText.setTextColor(android.graphics.Color.parseColor("#E60051"))

        binding.btnOptionOnlyDelivery.strokeColor = android.graphics.Color.parseColor("#ABBED1")
        binding.btnOptionOnlyDeliveryText.setTextColor(android.graphics.Color.parseColor("#000000"))

        binding.btnOptionOnlyTogo.strokeColor = android.graphics.Color.parseColor("#ABBED1")
        binding.btnOptionOnlyTogoText.setTextColor(android.graphics.Color.parseColor("#000000"))

        binding.imageviewIscheck1.setImageResource(R.drawable._c_checkmark_circle_selected)
        binding.imageviewIscheck2.setImageResource(R.drawable.ic_checkmark_circle)
        binding.imageviewIscheck2.setImageResource(R.drawable.ic_checkmark_circle)
        binding.btnAddColor.setBackgroundColor(android.graphics.Color.parseColor("#E60051"))

        ischeck = true

    }

    fun btnOptionOnlyDelivery() {
        binding.btnOptionAllAble.strokeColor = android.graphics.Color.parseColor("#ABBED1")
        binding.btnOptionAllAbleText.setTextColor(android.graphics.Color.parseColor("#000000"))

        binding.btnOptionOnlyDelivery.strokeColor = android.graphics.Color.parseColor("#E60051")
        binding.btnOptionOnlyDeliveryText.setTextColor(android.graphics.Color.parseColor("#E60051"))

        binding.btnOptionOnlyTogo.strokeColor = android.graphics.Color.parseColor("#ABBED1")
        binding.btnOptionOnlyTogoText.setTextColor(android.graphics.Color.parseColor("#000000"))

        binding.imageviewIscheck1.setImageResource(R.drawable.ic_checkmark_circle)
        binding.imageviewIscheck2.setImageResource(R.drawable._c_checkmark_circle_selected)
        binding.imageviewIscheck3.setImageResource(R.drawable.ic_checkmark_circle)

        binding.btnAddColor.setBackgroundColor(android.graphics.Color.parseColor("#E60051"))

        ischeck = true

    }

    fun btnOptionOnlyTogo() {
        binding.btnOptionAllAble.strokeColor = android.graphics.Color.parseColor("#ABBED1")
        binding.btnOptionAllAbleText.setTextColor(android.graphics.Color.parseColor("#000000"))

        binding.btnOptionOnlyDelivery.strokeColor = android.graphics.Color.parseColor("#ABBED1")
        binding.btnOptionOnlyDeliveryText.setTextColor(android.graphics.Color.parseColor("#000000"))

        binding.btnOptionOnlyTogo.strokeColor = android.graphics.Color.parseColor("#E60051")
        binding.btnOptionOnlyTogoText.setTextColor(android.graphics.Color.parseColor("#E60051"))

        binding.imageviewIscheck1.setImageResource(R.drawable.ic_checkmark_circle)
        binding.imageviewIscheck2.setImageResource(R.drawable.ic_checkmark_circle)
        binding.imageviewIscheck3.setImageResource(R.drawable._c_checkmark_circle_selected)
        binding.btnAddColor.setBackgroundColor(android.graphics.Color.parseColor("#E60051"))
        ischeck = true

    }

    fun cardviewAdd() {
        if (binding.edittextMenuName.text.toString() == "") {
            Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()
        } else if (binding.textviewMenuCategory.text.toString() == "") {
            Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

        } else if (binding.edittextPrice.text.toString() == "") {
            Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

        } else if (binding.edittextMenuName.text.toString() == "") {
            Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

        } else if (ischeck == false) {
            Toast.makeText(context, "체크 표시 해주세요.", Toast.LENGTH_SHORT).show()

        } else {
            //이동

        }
    }


}