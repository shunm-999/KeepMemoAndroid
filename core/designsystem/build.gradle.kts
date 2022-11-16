plugins {
    id("keepmemo.android.library")
    id("keepmemo.android.library.compose")
    id("keepmemo.android.spotless")
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.bundles.androidx.lifecycle)

    api(libs.bundles.androidx.compose.core)
    api(libs.bundles.androidx.compose.accompanist)
    api(libs.androidx.compose.activity)
    api(libs.androidx.compose.navigation)
    api(libs.androidx.compose.constraint)
    api(libs.androidx.compose.lifecycle.viewmodel)
    api(libs.androidx.compose.paging)
    api(libs.androidx.compose.coil)
    api(libs.androidx.compose.icons.extended)

    debugApi(libs.androidx.compose.ui.tooling)
}
