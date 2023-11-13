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
import com.example.meetup.databinding.FragmentCertificationBinding
import com.example.meetup.dialog.DialogEnrollStore

class CertificationFragment : Fragment() {
    lateinit var binding: FragmentCertificationBinding
    lateinit var certificationActivity: CertificationActivity

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
        certificationActivity = activity as CertificationActivity

        initView()

        requestPermissions(permissionList,0)


        return binding.root
    }

    fun initView() {
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

                }
            }

            buttonRecapture.visibility = View.INVISIBLE
            imageviewPhoto.visibility = View.GONE
        }
    }
}