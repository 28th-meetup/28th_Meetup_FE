package com.example.meetup.fragment

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.meetup.CameraSurfaceView
import com.example.meetup.R
import com.example.meetup.Util.fromDpToPx
import com.example.meetup.activity.CertificationActivity
import com.example.meetup.activity.HomeActivity
import com.example.meetup.databinding.FragmentCertificationBinding
import com.example.meetup.dialog.DialogEnrollStore
import kotlin.concurrent.fixedRateTimer

class CertificationFragment : Fragment() {
    lateinit var binding: FragmentCertificationBinding
    lateinit var homeActivity: HomeActivity

    private lateinit var cameraSurfaceView: CameraSurfaceView

    // 확인할 권한 목록
    val permissionList = arrayOf(
        Manifest.permission.CAMERA
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCertificationBinding.inflate(layoutInflater)
        homeActivity = activity as HomeActivity

        initView()

        requestPermissions(permissionList,0)


        val preview = CameraSurfaceView(requireContext())
        cameraSurfaceView = preview
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            274f.fromDpToPx()
        )

        // 뷰를 레이아웃에 추가
        binding.linearlayoutCamera.addView(preview, layoutParams)


        binding.run {
            buttonCapture.setOnClickListener {
                val dialog = DialogEnrollStore(homeActivity.supportFragmentManager)
                // 알림창이 띄워져있는 동안 배경 클릭 막기
                dialog.isCancelable = false
                homeActivity?.let { dialog.show(it.supportFragmentManager, "EnrollStoreDialog") }
            }
        }

        return binding.root
    }

    fun initView() {

        homeActivity.hideBottomNavigation(true)

        binding.run {

            toolbar.run {
                // back 버튼 설정
                setNavigationIcon(R.drawable.ic_back)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
            imageviewPhoto.visibility = View.GONE
        }
    }

    fun onCaptureClick() {
        // 사진 찍기
        cameraSurfaceView.takePicture(Camera.PictureCallback { data, camera ->
            // 사진이 찍힌 후의 처리를 여기에 추가하세요
            // data: JPEG 이미지 데이터
            Log.d("showPhoto","click")
            showPhoto(data)
        })
    }

    private fun showPhoto(data: ByteArray) {
        // JPEG 이미지 데이터를 Bitmap으로 디코딩
        val bitmap: Bitmap? = BitmapFactory.decodeByteArray(data, 0, data.size)

        // Bitmap을 ImageView에 설정
        binding.imageviewPhoto.setImageBitmap(bitmap)
    }
}