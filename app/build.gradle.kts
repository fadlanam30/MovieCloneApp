import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.androidx.navigation.safeargs)
}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()
localProperties.load(FileInputStream(localPropertiesFile))

android {
    namespace = "tech.fadlan.moviecloneapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "tech.fadlan.moviecloneapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "TMDB_BASE_URL", localProperties["TMDB_BASE_URL"] as String)
        buildConfigField(
            "String",
            "TMDB_IMAGE_BASE_URL",
            localProperties["TMDB_IMAGE_BASE_URL"] as String
        )
        buildConfigField(
            "String",
            "Authorization_Token",
            localProperties["Authorization_Token"] as String
        )
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
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    coreLibraryDesugaring(libs.desugar.jdk.libs)
    // Core Component
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Material
    implementation(libs.material)

    // Layout
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Navigation
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Network
    implementation(libs.bundles.square)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.common.android)
    ksp(libs.square.moshi.codegen)

    // di
    implementation(libs.google.hilt.android)
    ksp(libs.google.dagger.compiler)
    ksp(libs.google.hilt.compiler)

    //3rd Party
    implementation(libs.glide.core)
    implementation(libs.groupie.core)
    implementation(libs.groupie.viewbinding)
    implementation(libs.viewbinding.propertydelegate)
    implementation(libs.pierfrancescosoffritti.androidyoutubeplayer)
    implementation(libs.airbnb.lootie)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}