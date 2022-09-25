plugins {
    id("keepmemo.android.library")
    kotlin("kapt")
    id("keepmemo.android.spotless")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.compiler)

    implementation(libs.timber)

    implementation(libs.bundles.unit.test)
    kapt(libs.hilt.testing.compiler)
}