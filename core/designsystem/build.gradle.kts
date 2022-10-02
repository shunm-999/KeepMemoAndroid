plugins {
    id("keepmemo.android.library")
    id("keepmemo.android.library.compose")
    id("keepmemo.android.spotless")
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.bundles.androidx.lifecycle)

    api(libs.bundles.compose.core)
    api(libs.bundles.compose.accompanist)
    api(libs.compose.activity)
    api(libs.compose.navigation)
    api(libs.compose.constraint)
    api(libs.compose.lifecycle.viewmodel)
    api(libs.compose.paging)
    api(libs.compose.coil)
    api(libs.compose.icons.extended)

    debugApi(libs.compose.test.ui.tooling)
}
