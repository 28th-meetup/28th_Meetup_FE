package com.example.meetup.sharedPreference

import android.app.Application
import com.example.meetup.BuildConfig
import com.kakao.sdk.common.KakaoSdk

class MyApplication : Application() {
    companion object{
        lateinit var preferences: PreferenceUtil
    }

    override fun onCreate() {
        preferences = PreferenceUtil(applicationContext)
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)

    }
}