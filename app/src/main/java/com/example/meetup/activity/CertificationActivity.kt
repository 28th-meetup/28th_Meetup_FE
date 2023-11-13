package com.example.meetup.activity

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import com.example.meetup.CameraSurfaceView
import com.example.meetup.R
import com.example.meetup.Util.fromDpToPx
import com.example.meetup.databinding.ActivityAuthBinding
import com.example.meetup.databinding.ActivityCertificationBinding
import com.example.meetup.dialog.DialogEnrollStore
import com.example.meetup.fragment.HomeFragment
import java.security.Permission


class CertificationActivity : AppCompatActivity() {

    lateinit var binding: ActivityCertificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCertificationBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }
}