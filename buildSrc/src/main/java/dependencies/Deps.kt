package dependencies

object Deps {

    object AndroidX {

        private const val version = "1.7.0"
        const val core = "androidx.core:core-ktx:$version"

        object Compose {
            private const val version = "1.2.0-beta01"
            const val ui = "androidx.compose.ui:ui:$version"
            const val material = "androidx.compose.material:material:$version"
            const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
            const val livedata = "androidx.compose.runtime:runtime-livedata:$version"

            object Test {
                const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:$version"
                const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
            }
        }

        object Constraint {
            private const val version = "1.0.0"
            const val core = "androidx.constraintlayout:constraintlayout-compose:${version}"
        }

        object Lifecycle {
            private const val version = "2.4.0"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Activity {
            private const val version = "1.4.0"
            const val compose = "androidx.activity:activity-compose:$version"
        }

        object Navigation {
            private const val version = "2.4.0"
            const val navigation = "androidx.navigation:navigation-compose:$version"
        }

        object Paging {
            private const val version = "3.1.0"
            const val runtime = "androidx.paging:paging-runtime:$version"
            const val jetpackCompose = "androidx.paging:paging-compose:1.0.0-alpha14"
        }

        object Icons {
            private const val version = "1.1.1"
            const val extended = "androidx.compose.material:material-icons-extended:$version"
        }

        object Room {
            private const val version = "2.4.2"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
        }

        object Test {
            const val jUnit = "androidx.test.ext:junit:1.1.3"
            const val espresso = "androidx.test.espresso:espresso-core:3.4.0"

            private const val version = "1.2.0"

            const val core = "androidx.test:core:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"
            const val junit = "androidx.test.ext:junit:1.1.1"
            const val truth = "androidx.test.ext:truth:$version"
        }
    }

    object Accompanist {
        private const val version = "0.24.8-beta"

        const val uiController = "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val permissions = "com.google.accompanist:accompanist-permissions:$version"
        const val swiperRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val pager = "com.google.accompanist:accompanist-pager:$version"
        const val webview = "com.google.accompanist:accompanist-webview:$version"
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:1.4.0"
    }

    object Hilt {
        private const val version = "2.39"

        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val testing = "com.google.dagger:hilt-android-testing:$version"

        object Navigation {
            private const val version = "1.0.0"
            const val navigation = "androidx.hilt:hilt-navigation-compose:1.0.0"
        }
    }

    object JUnit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }

    object Timber {
        private const val version = "5.0.1"
        const val core = "com.jakewharton.timber:timber:$version"
    }

    object Ktlint {
        private const val version = "0.44.0"
        const val pinterest = "com.pinterest:ktlint:$version"
    }

    object OpenLicense {
        const val version = "1.2.8"
        const val licenseTools = "com.cookpad.android.plugin.license-tools"
    }
}