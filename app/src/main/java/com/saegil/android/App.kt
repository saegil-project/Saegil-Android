package com.saegil.android

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Log.d("빌드타입", "디버그 빌드입니다.")
        } else {
            Log.d("빌드타입", "릴리즈 빌드입니다.")
        }
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}