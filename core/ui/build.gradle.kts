plugins {
    id("keepmemo.android.library")
    id("keepmemo.android.library.compose")
    id("keepmemo.android.spotless")
}

android {
    namespace = "core.ui"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.bundles.androidx.lifecycle)

    implementation(libs.bundles.androidx.compose.core)
    implementation(libs.bundles.androidx.compose.accompanist)
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.constraint)
    implementation(libs.androidx.compose.lifecycle.viewmodel)
    implementation(libs.androidx.compose.lifecycle.runtime)
    implementation(libs.androidx.compose.paging)
    implementation(libs.androidx.compose.coil)
    implementation(libs.androidx.compose.icons.extended)
}