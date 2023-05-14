plugins {
    id("keepmemo.android.library")
    id("keepmemo.android.spotless")
    id("keepmemo.android.hilt")
}

android {
    namespace = "core.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    testImplementation(project(":core:testing"))

    implementation(libs.kotlinx.coroutines.android)
}