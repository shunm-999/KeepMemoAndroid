plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
}
gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "keepmemo.android.application.compose"
            implementationClass = "com.example.build_logic.AndroidApplicationComposePlugin"
        }
        register("androidApplication") {
            id = "keepmemo.android.application"
            implementationClass = "com.example.build_logic.AndroidApplicationPlugin"
        }
        register("androidLibraryCompose") {
            id = "keepmemo.android.library.compose"
            implementationClass = "com.example.build_logic.AndroidLibraryComposePlugin"
        }
        register("androidLibrary") {
            id = "keepmemo.android.library"
            implementationClass = "com.example.build_logic.AndroidLibraryPlugin"
        }
        register("androidFeature") {
            id = "keepmemo.android.feature"
            implementationClass = "com.example.build_logic.AndroidFeaturePlugin"
        }
        register("androidTest") {
            id = "keepmemo.android.test"
            implementationClass = "com.example.build_logic.AndroidTestPlugin"
        }
        register("androidHilt") {
            id = "keepmemo.android.hilt"
            implementationClass = "com.example.build_logic.AndroidHiltPlugin"
        }
        register("spotless") {
            id = "keepmemo.android.spotless"
            implementationClass = "com.example.build_logic.SpotlessPlugin"
        }
    }
}
