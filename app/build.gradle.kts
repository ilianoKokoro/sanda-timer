import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

val versionMajor = 1
val versionMinor = 4
val versionPatch = 0

val beta: Boolean = (project.findProperty("beta") as String?)?.toBoolean() ?: true


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

kotlinExtension.jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
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
        minSdk = 26
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

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.material3)
    debugImplementation(libs.androidx.ui.tooling)

    //  Viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Splashscreen
    implementation(libs.core.splashscreen)
}