plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    kotlin ("android")
    kotlin ("kapt")
}

android {
    namespace = "com.nicolasrodf.habitsapp"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.nicolasrodf.habitsapp"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.nicolasrodf.habitsapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core:core_presentation"))
    implementation(project(":core:core_data"))
    implementation(project(":settings:settings_presentation"))
    implementation(project(":onboarding:onboarding_presentation"))
    implementation(project(":onboarding:onboarding_data"))
    implementation(project(":onboarding:onboarding_domain"))
    implementation(project(":authentication:authentication_presentation"))
    implementation(project(":authentication:authentication_data"))
    implementation(project(":authentication:authentication_domain"))


    //Get day of week api 25 or lower
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.core)
    implementation(libs.clock)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Compose Navigation
    implementation(libs.navigation.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)

    // Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.androidx.hilt.compiler)

    // Coil
    implementation(libs.coil.compose)

    // Permissions
    implementation(libs.accompanist.permissions)

    // Room
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.logging.interceptor)

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    kaptAndroidTest(libs.hilt.android.compiler)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.hilt.android.testing)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.work.testing)
}