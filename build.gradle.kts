buildscript {
    val versionMajor = 1
    val versionMinor = 0
    val versionPatch = 0
    val versionBuild = 0

    val versionCode by extra { versionMajor * 10000 + versionMinor * 100 + versionPatch * 10 + versionBuild }
    val versionName by extra { "${versionMajor}.${versionMinor}.${versionPatch}" }

    dependencies {
        classpath(libs.hilt.gradle)
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.cookpad.license.tools)
    alias(libs.plugins.spotless) apply false
}

licenseTools {
    ignoredProjects = setOf(":core:testing")
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}