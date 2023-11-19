package com.example.meetup.fragment

import android.content.Intent
import android.graphics.Bitmap
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
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentMenuAddBinding
import com.example.meetup.databinding.FragmentStoreDetailBinding
import com.example.meetup.model.MenuAddRequestModelDto
import com.example.meetup.model.MenuAddRequestModelDtoFoodOptionRequestList
import com.example.meetup.model.food.MenuAddResponseModel
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class MenuAddFragment : Fragment() {

    private var _binding: FragmentMenuAddBinding? = null
    private val binding get() = _binding!!

    lateinit var homeActivity: HomeActivity
    private lateinit var API: APIS

    var menuName = ""
    var menuCategory = 0L
    var dollarPrice = 0L
    var canadaPrice = 0L

    lateinit var bitmap1: Bitmap
    lateinit var bitmap2: Bitmap

    var menuImageUrl = ""
    var description = ""
    var optionNum = 1
    var foodPackage = ""
    var ingredient = ""
    var ischeck = false
    var informationImage = ""
    var foodOptionRequestList = ArrayList<MenuAddRequestModelDtoFoodOptionRequestList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeActivity = activity as HomeActivity

        homeActivity.hideBottomNavigation(true)
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

                    bitmap1 = BitmapFactory.decodeStream(
                        requireContext().contentResolver.openInputStream(uri)
                    )

                    Glide.with(this)
                        .load(uri)
                        .into(binding.imageviewChooseRepresent)

                    menuImageUrl = uri.toString()

                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        val pickMedia2 =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")

                    bitmap2 = BitmapFactory.decodeStream(
                        requireContext().contentResolver.openInputStream(uri)
                    )

                    Glide.with(this)
                        .load(uri)
                        .into(binding.imageviewInfoRepresent)

                    informationImage = uri.toString()

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

        binding.imageviewInfoRepresent.setOnClickListener {
            pickMedia2.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

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
            menuCategory = 1L
        }

        binding.textviewCaterogy2.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy2.text
            binding.linearLayoutCategory.visibility = View.GONE
            menuCategory = 2L

        }
        binding.textviewCaterogy3.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy3.text
            binding.linearLayoutCategory.visibility = View.GONE
            menuCategory = 3L

        }
        binding.textviewCaterogy4.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy4.text
            binding.linearLayoutCategory.visibility = View.GONE
            menuCategory = 4L

        }
        binding.textviewCaterogy5.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy5.text
            binding.linearLayoutCategory.visibility = View.GONE
            menuCategory = 5L

        }
        binding.textviewCaterogy6.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy7.text
            binding.linearLayoutCategory.visibility = View.GONE
            menuCategory = 6L

        }
        binding.textviewCaterogy7.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy8.text
            binding.linearLayoutCategory.visibility = View.GONE
            menuCategory = 7L

        }
        binding.textviewCaterogy8.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy8.text
            binding.linearLayoutCategory.visibility = View.GONE
            menuCategory = 8L

        }
        binding.textviewCaterogy9.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy9.text
            binding.linearLayoutCategory.visibility = View.GONE
            menuCategory = 9L

        }
        binding.textviewCaterogy10.setOnClickListener {
            binding.textviewMenuCategory.text = binding.textviewCaterogy10.text
            binding.linearLayoutCategory.visibility = View.GONE
            menuCategory = 10L

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

        foodPackage = "ALL"
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
        foodPackage = "DELIVERY"

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

        foodPackage = "PACKAGE"

    }

    fun cardviewAdd() {


        optionArray()

        if (binding.edittextMenuName.text.toString() == "") {
            Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()
        } else if (binding.textviewMenuCategory.text.toString() == "") {
            Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

        } else if (binding.edittextPrice.text.toString() == "") {
            Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

        } else if (binding.edittextMenuExplain.text.toString() == "") {
            Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

        } else if (menuImageUrl == "") {
            Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

        } else if (ischeck == false) {
            Toast.makeText(context, "체크 표시 해주세요.", Toast.LENGTH_SHORT).show()

        } else {
            //이동
            var dto = MenuAddRequestModelDto( menuCategory,
                binding.edittextMenuName.text.toString(),
                binding.edittextPrice.text.toString().toLong(),
                0L,
                binding.edittextMenuExplain.toString(),
                foodOptionRequestList,
                foodPackage,
                binding.edittextMenuIngredient.text.toString())


            postMenu(dto, bitmap1, bitmap2)



        }
    }


    fun optionArray() {
        when (optionNum) {
            0 -> {
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName.text.toString(),
                        binding.edittextPrice.text.toString().toLong(),
                        0L
                    )
                )
            }

            1 -> {
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName.text.toString(),
                        binding.edittextPrice.text.toString().toLong(),
                        0L
                    )
                )
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName2.text.toString(),
                        binding.edittextPrice1.text.toString().toLong(),
                        0L
                    )
                )

            }

            2 -> {
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName.text.toString(),
                        binding.edittextPrice.text.toString().toLong(),
                        0L
                    )
                )
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName2.text.toString(),
                        binding.edittextPrice1.text.toString().toLong(),
                        0L
                    )
                )
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName3.text.toString(),
                        binding.edittextPrice2.text.toString().toLong(),
                        0L
                    )
                )

            }

            3 -> {
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName.text.toString(),
                        binding.edittextPrice.text.toString().toLong(),
                        0L
                    )
                )
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName2.text.toString(),
                        binding.edittextPrice1.text.toString().toLong(),
                        0L
                    )
                )
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName3.text.toString(),
                        binding.edittextPrice2.text.toString().toLong(),
                        0L
                    )
                )
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName4.text.toString(),
                        binding.edittextPrice3.text.toString().toLong(),
                        0L
                    )
                )

            }

            4 -> {
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName.text.toString(),
                        binding.edittextPrice.text.toString().toLong(),
                        0L
                    )
                )
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName2.text.toString(),
                        binding.edittextPrice1.text.toString().toLong(),
                        0L
                    )
                )
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName3.text.toString(),
                        binding.edittextPrice2.text.toString().toLong(),
                        0L
                    )
                )
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName4.text.toString(),
                        binding.edittextPrice3.text.toString().toLong(),
                        0L
                    )
                )
                foodOptionRequestList.add(
                    MenuAddRequestModelDtoFoodOptionRequestList(
                        binding.edittextOptionName5.text.toString(),
                        binding.edittextPrice4.text.toString().toLong(),
                        0L
                    )
                )

            }

        }

    }

    private fun postMenu(


        dto: MenuAddRequestModelDto,
        image: Bitmap,
        informationImage: Bitmap
    ) {
        val mediaType = "image/jpeg".toMediaType()

        // Bitmap을 파일로 저장
        val file1 = File(requireContext().cacheDir, "image.jpg")
        val file2 = File(requireContext().cacheDir, "image.jpg")

        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        informationImage.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        file1.writeBytes(byteArray)
        file2.writeBytes(byteArray)


        val requestBody1 = file1.asRequestBody(mediaType)
        val requestBody2 = file2.asRequestBody(mediaType)


        val imagePart1 =
            MultipartBody.Part.createFormData("businessLicense", file1.name, requestBody1)
        val imagePart2 =
            MultipartBody.Part.createFormData("businessLicense", file2.name, requestBody2)


        Log.d("imagePart1", imagePart1.toString())
        Log.d("imagePart2", imagePart2.toString())


        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)
        val tokenManager = com.example.meetup.sharedPreference.TokenManager(requireContext())



        try {
            API.postMenu(tokenManager.getAccessToken().toString(),dto,imagePart1,imagePart2).enqueue(
                object : Callback<MenuAddResponseModel> {

                    override fun onResponse(call: Call<MenuAddResponseModel>, response: Response<MenuAddResponseModel>) {
                        if (response.isSuccessful) {
                            val menuAddSuccessFragment = MenuAddSuccessFragment()
                            fragmentManager?.beginTransaction()?.apply {
                                replace(R.id.frameArea, menuAddSuccessFragment)
                                commit()
                            }


                            Log.d(
                                "MenuAddResponseModel : ",
                                " success , ${response.body().toString()}"
                            )


                        } else {

                            Log.d(
                                "MenuAddResponseModel Response : ",
                                "fail 1 ${
                                    response.body().toString()
                                } , ${response.message()}, ${response.errorBody().toString()}"
                            )
                        }
                    }

                    override fun onFailure(call: Call<MenuAddResponseModel>, t: Throwable) {
                        Log.d("MenuAddResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                    }
                })
        } catch (e: Exception) {
            Log.d("MenuAddResponseModel response : ", " fail 3 , ${e.message}")
        }

    }

}