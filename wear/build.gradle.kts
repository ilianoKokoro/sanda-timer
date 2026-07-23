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
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.ksp)
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
        minSdk = 31
        targetSdk = 36
        versionCode = 1000000 + versionMajor * 10000 + versionMinor * 100 + versionPatch
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}-wear"
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
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling)
    implementation(libs.core.splashscreen)
    implementation(libs.guava)
    implementation(libs.play.services.wearable)
    implementation(libs.protolayout.material3)
    implementation(libs.tiles.tooling.preview)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.watchface.complications.data.source.ktx)
    implementation(libs.wear.tooling.preview)
    debugImplementation(libs.tiles.tooling)
    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.ui.tooling)

    // Wear OS Material + navigation
    implementation(libs.androidx.compose.navigation)

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


    // Wear OS
    implementation(libs.tiles)
    implementation(libs.protolayout)
    implementation(libs.protolayout.material)
    implementation(libs.protolayout.expression)
    debugImplementation(libs.tiles.renderer)

}