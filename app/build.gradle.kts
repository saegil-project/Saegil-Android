import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
}

val properties = Properties()
properties.load(FileInputStream(rootProject.file("local.properties")))

android {
    namespace = "com.saegil.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.saegil.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 7
        versionName = "1.0.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["NAVER_CLIENT_ID"] = properties.getProperty("naver_client_id")
        manifestPlaceholders["NATIVE_APP_KEY"] = properties.getProperty("kakao_native_app_key")
        buildConfigField("String", "NATIVE_APP_KEY", "\"${properties["kakao_native_app_key"]}\"")

        // JDK 11+에서만 필요
        multiDexEnabled = true
        manifestPlaceholders["jvmArgs"] = "-XX:-UseSplitVerifier"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            resValue("string", "app_name", "디버그 새길")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.navigation.compose) //navigation

    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    // Timber for logging
    implementation(libs.timber)

    //모듈 의존
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":core:designsystem"))

    implementation(project(":presentation:news"))
    implementation(project(":presentation:notice"))
    implementation(project(":presentation:learning"))
    implementation(project(":presentation:mypage"))
    implementation(project(":presentation:onboarding"))
    implementation(project(":presentation:splash"))
    implementation(project(":presentation:log"))

    implementation(libs.kakao.v2.all) // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation(libs.kakao.v2.user) // 카카오 로그인 API 모듈
}