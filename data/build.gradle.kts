import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.devtools.ksp)
}

val properties = Properties()
properties.load(FileInputStream(rootProject.file("local.properties")))

android {
    namespace = "com.saegil.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "BASE_URL", "\"${properties.getProperty("base_url")}\"")
    }

    buildTypes {
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.room.common.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio) // 기본 클라이언트 엔진
    implementation(libs.ktor.client.android) // Android 전용 엔진
    implementation(libs.ktor.client.logging) // 로깅
    implementation(libs.ktor.client.content.negotiation) // JSON 파싱
    implementation(libs.ktor.serialization.kotlinx.json) // kotlinx.serialization 사용
    implementation(libs.ktor.client.serialization)
    implementation(libs.kotlinx.serialization.json)

    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    //Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    implementation(libs.kakao.v2.all) // 전체 모듈 설치, 2.11.0 버전부터 지원

    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.room.compiler)
    implementation(libs.androidx.room.ktx) // Kotlin Extensions and Coroutines support for Room

    implementation(project(":domain"))//클린아키텍처 도메인 의존
}