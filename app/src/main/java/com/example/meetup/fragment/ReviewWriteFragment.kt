package com.example.meetup.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.meetup.R
import com.example.meetup.base.BaseFragment
import com.example.meetup.databinding.FragmentReviewWriteBinding


class ReviewWriteFragment : BaseFragment<FragmentReviewWriteBinding>(R.layout.fragment_review_write) {


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

//                        bitmap = BitmapFactory.decodeStream(
//                            requireContext().contentResolver.openInputStream(uri)
//                        )

//                        binding.textView8.setText("$uri")



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


        }
        binding.imageviewStar2.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star_gray)

        }
        binding.imageviewStar3.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star_gray)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star_gray)

        }
        binding.imageviewStar4.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star_gray)

        }
        binding.imageviewStar5.setOnClickListener {
            binding.imageviewStar1.setImageResource(R.drawable.ic_star)
            binding.imageviewStar2.setImageResource(R.drawable.ic_star)
            binding.imageviewStar3.setImageResource(R.drawable.ic_star)
            binding.imageviewStar4.setImageResource(R.drawable.ic_star)
            binding.imageviewStar5.setImageResource(R.drawable.ic_star)

        }
    }


}