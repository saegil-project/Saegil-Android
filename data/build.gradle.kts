import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.protobuf)
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
        buildConfigField("String", "OPEN_AI_API_KEY", "\"${properties.getProperty("openai_api_key")}\"")
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
    sourceSets {
        getByName("main") {
            java.srcDirs(
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/kotlin"
            )
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.29.2"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.kotlinx.serialization.json)
    implementation(project(":core:common"))
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

    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    //Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    implementation(libs.kakao.v2.all) // 전체 모듈 설치, 2.11.0 버전부터 지원

    //Preferences DataStore/Proto
    implementation(libs.androidx.datastore)
    implementation(libs.protobuf.javalite)
    implementation(libs.androidx.datastore.preferences)

    implementation(project(":domain"))//클린아키텍처 도메인 의존

    implementation("io.ktor:ktor-client-websockets:2.3.5")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")


}