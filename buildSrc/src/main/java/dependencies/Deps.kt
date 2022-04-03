package dependencies

object Deps {

    object AndroidX {

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
    }

    object Accompanist {
        const val version = "0.23.0"

        const val uiController = "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val swiperRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val pager = "com.google.accompanist:accompanist-pager:$version"
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

    object Timber {
        private const val version = "5.0.1"
        const val core = "com.jakewharton.timber:timber:$version"
    }

    object Ktlint {
        private const val version = "0.44.0"
        const val pinterest = "com.pinterest:ktlint:$version"
    }
}