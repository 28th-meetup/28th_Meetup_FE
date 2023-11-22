package com.example.meetup.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.activity.HomeActivity
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentOrderListBinding
import com.example.meetup.databinding.FragmentReviewWriteBinding
import com.example.meetup.model.GetOrderListResponseModel
import com.example.meetup.model.PostReivewRequestModel
import com.example.meetup.model.PostReviewResponseModel
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import com.example.meetup.viewmodel.OrderListViewModel
import com.kakao.sdk.talk.model.Order
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class ReviewWriteFragment :
    Fragment() {
    private lateinit var API : APIS

    private var _binding: FragmentReviewWriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OrderListViewModel

    lateinit var bitmap1: Bitmap


    lateinit var homeActivity: HomeActivity
    var imageUrl = ""

    var star_rate = 0
    var review_content = ""
    var storeId = 0L
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

        _binding = FragmentReviewWriteBinding.inflate(inflater, container, false)
        val view = binding.root


        storeId = viewModel.storeId.value!!.toLong()
        Log.d("storeId","$storeId")


        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")

                        bitmap1 = BitmapFactory.decodeStream(
                            requireContext().contentResolver.openInputStream(uri)
                        )

//                        binding.textView8.setText("$uri")


                    Glide.with(this)
                        .load(uri)
                        .into(binding.imageviewReviewImage)

                    imageUrl = uri.toString()

                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        binding.imageviewReviewImage.setOnClickListener {

            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))


        }

        binding.imageviewStar1.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star_gray)

            star_rate = 1

        }
        binding.imageviewStar2.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star_gray)

            star_rate = 2

        }
        binding.imageviewStar3.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star_gray)

            star_rate = 3

        }
        binding.imageviewStar4.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star_gray)

            star_rate = 4

        }
        binding.imageviewStar5.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star)

            star_rate = 5

        }

        binding.btnX.setOnClickListener {
            btnXClick()
        }

        binding.textviewWrite.setOnClickListener {

            textviewWriteClick()
        }

        return view

    }


    fun btnXClick() {
        val orderListFragment = OrderListFragment()
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.frameArea, orderListFragment)
            commit()
        }
    }

    fun textviewWriteClick() {

        review_content = binding.edittextReviewContent.text.toString()


        if (star_rate == 0) {
            Toast.makeText(context, "별점을 선택해주세요.", Toast.LENGTH_SHORT).show()
        } else if (binding.edittextReviewContent.length() < 20) {
            Toast.makeText(context, "내용을 최소 20자 이상 작성해주세요.", Toast.LENGTH_SHORT).show()

        } else {
            //통신

            val mediaType = "image/jpeg".toMediaType()

            // Bitmap을 파일로 저장
            val file1 = File(requireContext().cacheDir, "image.jpg")

            val stream = ByteArrayOutputStream()
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray = stream.toByteArray()
            file1.writeBytes(byteArray)


            val requestBody1 = file1.asRequestBody(mediaType)


            val imagePart1 =
                MultipartBody.Part.createFormData("image", file1.name, requestBody1)

            API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

                val tokenManager = com.example.meetup.sharedPreference.TokenManager(requireContext())   //가게 목록 가져오기

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

                Log.d("tokenManager", tokenManager.getAccessToken().toString())
                    try{
                        API.postReview(tokenManager.getAccessToken().toString(),
                            PostReivewRequestModel(storeId,star_rate.toLong(),review_content),
                            imagePart1
                        ).enqueue(
                            object : Callback<PostReviewResponseModel> {

                                override fun onResponse(call: Call<PostReviewResponseModel>, response: Response<PostReviewResponseModel>) {
                                    if (response.isSuccessful) {



                                        Log.d("PostReviewResponseModel : " , " success , ${response.body().toString()}")
                                    } else {

                                        Log.d("PostReviewResponseModel Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                                    }
                                }

                                override fun onFailure(call: Call<PostReviewResponseModel>, t: Throwable) {
                                    Log.d("PostReviewResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                                }
                            })
                    } catch (e:Exception) {
                        Log.d("PostReviewResponseModel response : ", " fail 3 , ${e.message}")
                    }
                }


    }


}