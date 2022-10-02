plugins {
    id("keepmemo.android.library")
    id("keepmemo.android.hilt")
    id("keepmemo.android.spotless")
    id("kotlin-kapt")
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}