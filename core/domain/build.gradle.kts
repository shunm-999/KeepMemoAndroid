plugins {
    id("keepmemo.android.library")
    id("keepmemo.android.spotless")
    id("keepmemo.android.hilt")
}

android {
    namespace = "core.domain"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    implementation(libs.kotlinx.coroutines.android)
}