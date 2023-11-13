package com.example.meetup

import android.content.Context
import android.hardware.Camera
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import android.view.WindowManager
import java.lang.Math.abs

class CameraSurfaceView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private var mCamera: Camera? = null

    init {
        // SurfaceView 초기화 및 Callback 설정
        holder.addCallback(this)
    }

    public override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d("CameraSurfaceView", "Surface created")

        // 카메라 열기
        mCamera = Camera.open()

        try {
            mCamera?.setPreviewDisplay(holder)

            // 미리보기 방향 및 비율 설정
            setCameraDisplayOrientation()
            setOptimalPreviewSize()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setOptimalPreviewSize() {
        val parameters = mCamera?.parameters
        val supportedSizes = parameters?.supportedPreviewSizes

        // 원하는 비율
        val targetRatio = 5.0 / 16.0

        // 가장 가까운 비율의 미리보기 크기 찾기
        var optimalSize: Camera.Size? = null
        var minDiff = Double.MAX_VALUE

        supportedSizes?.forEach { size ->
            val supportedRatio = size.width.toDouble() / size.height.toDouble()
            val diff = abs(supportedRatio - targetRatio)

            if (diff < minDiff) {
                optimalSize = size
                minDiff = diff
            }
        }

        // 찾은 크기를 설정
        optimalSize?.let {
            parameters?.setPreviewSize(it.width, it.height)
            mCamera?.parameters = parameters
        }
    }



    public override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // 미리보기 시작
        Log.d("CameraSurfaceView", "Surface changed")
        mCamera?.startPreview()
    }

    public override fun surfaceDestroyed(holder: SurfaceHolder) {
        // 미리보기 중지 및 카메라 닫기
        mCamera?.stopPreview()
        mCamera?.release()
        mCamera = null
    }

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 뷰의 크기를 300x300으로 제한
        val widthSize = resolveSize(100, widthMeasureSpec)
        val heightSize = resolveSize(100, heightMeasureSpec)
        setMeasuredDimension(widthSize, heightSize)
    }

    fun takePicture(callback: Camera.PictureCallback) {
        mCamera?.takePicture(null, null, callback)
    }

    private fun setCameraDisplayOrientation() {
        val info = Camera.CameraInfo()
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info)

        val rotation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation
        var degrees = 0

        when (rotation) {
            Surface.ROTATION_0 -> degrees = 0
            Surface.ROTATION_90 -> degrees = 90
            Surface.ROTATION_180 -> degrees = 180
            Surface.ROTATION_270 -> degrees = 270
        }

        var result: Int
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360
            result = (360 - result) % 360  // compensate the mirror
        } else {
            // back-facing
            result = (info.orientation - degrees + 360) % 360
        }

        mCamera?.setDisplayOrientation(result)
    }
}


