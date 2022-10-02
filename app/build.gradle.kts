plugins {
    id("keepmemo.android.application.compose")
    id("keepmemo.android.application")
    id("keepmemo.android.spotless")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    defaultConfig {
        applicationId = "com.example.keepmemo"
        versionCode = versionCode
        versionName = versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file("../debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        create("release") {
            val tmpFilePath = System.getProperty("user.home") + "/work/_temp/keystore/"
            val allFilesFromDir = file(tmpFilePath).listFiles()

            if (allFilesFromDir != null) {
                val keystoreFile = allFilesFromDir.first()
                keystoreFile.renameTo(file("keystore/upload-keystore.jks"))
            }

            storeFile = file("keystore/upload-keystore.jks")
            storePassword = System.getenv("SIGNING_STORE_PASSWORD")
            keyAlias = System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
        }
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:data"))

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

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.compiler)

    implementation(libs.timber)

    testImplementation(libs.bundles.unit.test)
    kaptTest(libs.hilt.testing.compiler)

    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.compose.test.ui.tooling)
}