plugins {
    id("keepmemo.android.library")
    id("keepmemo.android.spotless")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
    }
}


dependencies {
    implementation(libs.google.gson)
    implementation(libs.bundles.androidx.credentials.manager)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    androidTestImplementation(libs.bundles.android.test)
}