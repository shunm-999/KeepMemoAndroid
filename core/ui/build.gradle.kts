plugins {
    id("keepmemo.android.library")
    id("keepmemo.android.library.compose")
    id("keepmemo.android.spotless")
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.bundles.androidx.lifecycle)

    implementation(libs.bundles.compose.core)
    implementation(libs.bundles.compose.accompanist)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.compose.constraint)
    implementation(libs.compose.lifecycle.viewmodel)
    implementation(libs.compose.paging)
    implementation(libs.compose.coil)
    implementation(libs.compose.icons.extended)
}