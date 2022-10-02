plugins {
    id("keepmemo.android.library")
    kotlin("kapt")
    id("keepmemo.android.spotless")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core:model"))

    api(libs.hilt.android)
    api(libs.hilt.navigation)
    kapt(libs.hilt.compiler)

    api(libs.timber)

    api(libs.bundles.unit.test)
    kapt(libs.hilt.testing.compiler)
}