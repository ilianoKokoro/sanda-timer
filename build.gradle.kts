// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
}

extra["versionMajor"] = 0
extra["versionMinor"] = 2
extra["versionPatch"] = 0
extra["beta"] = true // TODO dynamic