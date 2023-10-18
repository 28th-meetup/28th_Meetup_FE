package com.example.meetup.sharedPreference

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class MyApplication : Application() {
    companion object{
        lateinit var preferences: PreferenceUtil
    }

    override fun onCreate() {
        preferences = PreferenceUtil(applicationContext)
        super.onCreate()

        KakaoSdk.init(this, "2cd17fd1458fded657e0a1fd6ea32be2")

    }
}