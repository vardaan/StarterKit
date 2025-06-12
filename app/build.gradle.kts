plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id ("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "1.9.0" // match your Kotlin version

}

android {
    namespace = "com.mobiledev.starterkit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mobiledev.starterkit"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        compose = true
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Retrofit core + Gson converter
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // OkHttp & Logging Interceptor
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.timber)


    implementation (libs.androidx.lifecycle.viewmodel.ktx)  // :contentReference[oaicite:0]{index=0}
    implementation (libs.androidx.lifecycle.runtime.ktx)     // :contentReference[oaicite:1]{index=1}
    implementation (libs.androidx.lifecycle.livedata.ktx)   // :contentReference[oaicite:2]{index=2}
    implementation (libs.androidx.lifecycle.viewmodel.savedstate)  // :contentReference[oaicite:3]{index=3}
    implementation (libs.androidx.lifecycle.viewmodel.compose)     // :contentReference[oaicite:4]{index=4}
    implementation (libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation (libs.hilt.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.cronet.embedded)
    kapt (libs.hilt.compiler)

    implementation (libs.glide)
    implementation (libs.compose)
    implementation(libs.androidx.work.runtime.ktx) // Kotlin + coroutines

    implementation(libs.androidx.work.gcm)         // legacy GCM
    androidTestImplementation(libs.androidx.work.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
