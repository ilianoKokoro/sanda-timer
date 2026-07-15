val versionMajor = 0
val versionMinor = 0
val versionPatch = 1

val beta: Boolean = (project.findProperty("beta") as String?)?.toBoolean() ?: true

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ca.ilianokokoro.sanda_timer"
    compileSdk {
        version = release(37)
    }
    ndkVersion = "30.0.14904198"
    buildToolsVersion = "37.0.0"

    defaultConfig {
        applicationId = "ca.ilianokokoro.sanda_timer"
        minSdk = 30
        targetSdk = 37
        versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}${if (beta) "-beta" else ""}"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    useLibrary("wear-sdk")
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling)
    implementation(libs.core.splashscreen)
    implementation(libs.guava)
    implementation(libs.play.services.wearable)
    implementation(libs.protolayout)
    implementation(libs.protolayout.material3)
    implementation(libs.tiles)
    implementation(libs.tiles.tooling.preview)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.watchface.complications.data.source.ktx)
    implementation(libs.wear.tooling.preview)
    debugImplementation(libs.tiles.renderer)
    debugImplementation(libs.tiles.tooling)
    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.ui.tooling)

    // Notification compatibility
    implementation(libs.androidx.appcompat)
    implementation(libs.activity.ktx)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //  Serialization
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    // Navigation 3
    implementation(libs.nav3.runtime)
    implementation(libs.nav3.ui)
    implementation(libs.nav3.wear)

    implementation(libs.nav3.viewmodel)

    // Viewmodel
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Icons
    implementation(libs.androidx.material.icons.extended)

    // Ongoing
    implementation(libs.androidx.core)
    implementation(libs.androidx.wear.ongoing)

}