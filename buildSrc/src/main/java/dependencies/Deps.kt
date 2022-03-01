package dependencies

object Deps {

    object AndroidX {
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
    }

    object Accompanist {
        const val version = "0.23.0"
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

    object Ktlint {
        private const val version = "0.44.0"
        const val pinterest = "com.pinterest:ktlint:$version"
    }
}