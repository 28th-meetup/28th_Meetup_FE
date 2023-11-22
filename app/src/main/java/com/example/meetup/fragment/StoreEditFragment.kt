package com.example.meetup.fragment

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.example.meetup.activity.HomeActivity
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentMenuAddBinding
import com.example.meetup.databinding.FragmentMenuEditBinding
import com.example.meetup.databinding.FragmentStoreEditBinding
import com.example.meetup.model.PostStoreResponseModel
import com.example.meetup.model.store.GetStoreListStores
import com.example.meetup.model.store.PostStoreDtoRequestModel
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.sharedPreference.TokenManager
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class StoreEditFragment : Fragment() {


   private lateinit var API : APIS

    var name = ""
    var minOrderAmount = 0L
    var description = ""
    var countryPhoneCode = ""
    var phoneNum = ""
    var globalRegionId = 0L
    var address = ""
    var detailAddress = ""
    var deliveryRegion = ""
    var operationTime = ""
    var foodChangeYn = false
    var koreanYn = false

    var globalRegion = 0L
    var isClickSpinner = false
    var imageNum = 0

    private val imagesList: MutableList<Uri> = mutableListOf()

    var isCheck = false
    private var _binding: FragmentStoreEditBinding? = null
    private val binding get() = _binding!!
    lateinit var homeActivity: HomeActivity
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

        _binding = FragmentStoreEditBinding.inflate(inflater, container, false)
        val view = binding.root


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

        var loactionSpinner = binding.spinnerLocation    // spinner
        var locationArray = resources.getStringArray(R.array.location_array)    // 배열
        setSpinner(loactionSpinner, locationArray)    // 스피너 설정
        var phoneSpinner = binding.spinnerPhoneNumber    // spinner
        var phoneArray = resources.getStringArray(R.array.phone_array)    // 배열
        setSpinner(phoneSpinner, phoneArray)    // 스피너 설정
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uris != null) {
                    Log.d("PhotoPicker", "Selected URI: $uris")

//                    Glide.with(this)
//                        .load(uri)
//                        .into(binding.imageviewChooseRepresent)

                    imageNum = uris.size

                    imagesList.clear()

                    for(i in 0 until imageNum) {
                        imagesList.add(uris.get(i))
                    }


                    when (uris.size) {
                        1 -> {
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

                        2 -> {
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

                        3 -> {
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

        binding.spinnerPhoneNumber.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        isClickSpinner = false

                    }

                    1 -> {
                        isClickSpinner = true
                        countryPhoneCode = "KOREA"
                    }

                    2 -> {
                        isClickSpinner = true
                        countryPhoneCode = "USA"
                    }

                    3 -> {
                        isClickSpinner = true
                        countryPhoneCode = "CANADA"
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.spinnerLocation.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {
                        isClickSpinner = false

                    }
                    else -> {
                        isClickSpinner = true
                        globalRegionId = position.toLong()

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

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

            name = binding.edittextStoreName.text.toString()
            minOrderAmount = binding.edittextMinimumPrice.text.toString().toLong()
            description = binding.edittextStoreExplain.text.toString()
            countryPhoneCode = "KOREA"
            phoneNum = binding.edittextPhoneNumber.text.toString()
            globalRegion = globalRegionId
            address = binding.edittextLocation1.text.toString()
            detailAddress = binding.edittextLocation2.text.toString()
            deliveryRegion = binding.edittextDeliverAbleArea.text.toString()
            operationTime = binding.edittextRunTime.text.toString()

            koreanYn = true

            if (name == "") {
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if (imageNum != 3) {
                Toast.makeText(context, "사진 3개 선택해주세요.", Toast.LENGTH_SHORT).show()

            } else if (minOrderAmount == 0L) {
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if (description == "") {
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if (phoneNum == "") {
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if (address == "") {
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if (detailAddress == "") {
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if (deliveryRegion == "") {
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if (operationTime == "") {
                Toast.makeText(context, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            } else if (!isCheck) {
                Toast.makeText(context, "체크 표시 해주세요.", Toast.LENGTH_SHORT).show()

            } else if (!isClickSpinner) {
                Toast.makeText(context, "국가 번호 및 지역을 선택 해주세요.", Toast.LENGTH_SHORT).show()

            } else {

                postStore()


            }

        }

    }

    fun imageviewCheckYes() {
        binding.imageviewCheckYes.setImageResource(R.drawable.ic_check_square_check)
        binding.imageviewCheckNo.setImageResource(R.drawable.ic_checkbox_uncheck)
        binding.btnSaveColor.setBackgroundColor(Color.parseColor("#E60051"))
        foodChangeYn = true
        isCheck = true
    }

    fun imageviewCheckNo() {
        binding.imageviewCheckYes.setImageResource(R.drawable.ic_checkbox_uncheck)
        binding.imageviewCheckNo.setImageResource(R.drawable.ic_check_square_check)
        binding.btnSaveColor.setBackgroundColor(Color.parseColor("#E60051"))
        foodChangeYn = false
        isCheck = true


    }

    private fun setSpinner(spinner: Spinner, array: Array<String>) {
        var adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line
        ) {
            override fun getCount(): Int = super.getCount()
        }  // array에서 hint 안 보이게 하기
        adapter.addAll(array.toMutableList())   // 배열 추가
        spinner.adapter = adapter               // 어댑터 달기
    }


    fun postStore() {
        val mediaType = "image/jpeg".toMediaType()
        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)


        val tokenManager = TokenManager(requireContext())

        val imagesParts: MutableList<MultipartBody.Part> = mutableListOf()
        val contentResolver: ContentResolver = requireContext().contentResolver

        Log.d("밋업", "가게등록 이미지 : ${imagesList}")

        for (imageUri in imagesList) {
            val file = File(getRealPathFromURI(imageUri, contentResolver))
            val requestFile: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val body: MultipartBody.Part =
                MultipartBody.Part.createFormData("image", file.name, requestFile)

            imagesParts.add(body)
        }
        try {

            API.postStore(tokenManager.getAccessToken().toString(), PostStoreDtoRequestModel(name,minOrderAmount,description,countryPhoneCode,phoneNum,globalRegion,address,detailAddress,deliveryRegion,operationTime,foodChangeYn, koreanYn),imagesParts).enqueue(
                object : Callback<PostStoreResponseModel> {

                    override fun onResponse(call: Call<PostStoreResponseModel>, response: Response<PostStoreResponseModel>) {
                        if (response.isSuccessful) {

                            val storeAddSuccessFragment = StoreAddSuccessFragment()
                            fragmentManager?.beginTransaction()?.apply {
                                replace(R.id.frameArea, storeAddSuccessFragment)
                                commit()
                            }

                            Log.d("PostStoreResponseModel : " , " success , ${response.body().toString()}")
                        } else {

                            if (response.code() == 400) {
                                Snackbar.make(
                                    binding.root,
                                    "이미 존재하는 가게이거나,\n이미 가게를 생성한 유저입니다.",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }

                            Log.d("PostStoreResponseModel Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                        }
                    }

                    override fun onFailure(call: Call<PostStoreResponseModel>, t: Throwable) {
                        Log.d("PostStoreResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                    }
                })
        } catch (e:Exception) {
            Log.d("PostStoreResponseModel response : ", " fail 3 , ${e.message}")
        }
    }


    private fun getRealPathFromURI(uri: Uri, contentResolver: ContentResolver): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(columnIndex)
            cursor.close()
            return filePath
        }
        return uri.path ?: ""
    }

}