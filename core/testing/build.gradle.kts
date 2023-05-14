plugins {
    kotlin("kapt")
    id("keepmemo.android.library")
    id("keepmemo.android.library.compose")
    id("keepmemo.android.spotless")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "core.testing"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    api(libs.hilt.android)
    api(libs.hilt.navigation)
    kapt(libs.hilt.compiler)

    api(libs.timber)

    api(libs.bundles.unit.test)
    api(libs.bundles.android.test)
    kapt(libs.hilt.testing.compiler)

    debugApi(libs.androidx.compose.ui.testManifest)
}