import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import java.io.FileInputStream
import java.util.Properties

val keystorePropertiesFile: File = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}


val versionMajor: Int = rootProject.extra["versionMajor"] as Int
val versionMinor: Int = rootProject.extra["versionMinor"] as Int
val versionPatch: Int = rootProject.extra["versionPatch"] as Int


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
    buildToolsVersion = "37.0.0"
    defaultConfig {
        applicationId = "ca.ilianokokoro.sanda_timer"
        minSdk = 26
        targetSdk = 37
        versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
    }


    signingConfigs {
        if (keystorePropertiesFile.exists()) {
            create("release") {
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = rootProject.file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfigs.findByName("release")?.let {
                signingConfig = it
            }
            ndk {
                debugSymbolLevel = "FULL"
            }
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