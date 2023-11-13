package com.example.meetup.fragment

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
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentReviewWriteBinding
import com.kakao.sdk.talk.model.Order


class ReviewWriteFragment : BaseFragment<FragmentReviewWriteBinding>(R.layout.fragment_review_write) {


    var star_rate = 0
    var review_content = ""
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
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")

//                        bitmap = BitmapFactory.decodeStream(
//                            requireContext().contentResolver.openInputStream(uri)
//                        )

//                        binding.textView8.setText("$uri")

                    binding.textviewPhotoNum.setText("사진 ${uri.size}/10")
                    binding.textviewUri1.setText(uri.toString())
                    binding.textviewUri1.visibility = View.VISIBLE


                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        binding.btnChoosePhoto.setOnClickListener{

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

        binding.textviewWrite.setOnClickListener{

            textviewWriteClick()
        }
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


        if(star_rate==0){
            Toast.makeText(context,"별점을 선택해주세요.",Toast.LENGTH_SHORT).show()
        } else if (binding.edittextReviewContent.length()<20){
            Toast.makeText(context,"내용을 최소 20자 이상 작성해주세요.",Toast.LENGTH_SHORT).show()

        } else {
            //통신

        }
    }



}