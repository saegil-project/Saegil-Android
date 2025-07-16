package com.saegil.android

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import androidx.core.content.edit

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
        Timber.plant(Timber.DebugTree())
        Firebase.messaging.token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val deviceToken = task.result

                getSharedPreferences("fcm", MODE_PRIVATE)
                    .edit {
                        putString("deviceToken", deviceToken)
                    }
            }
        }
    }
}
