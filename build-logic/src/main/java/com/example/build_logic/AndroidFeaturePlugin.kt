package com.example.build_logic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeaturePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("keepmemo.android.library")
                apply("keepmemo.android.hilt")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", project(":core:model"))
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:data"))
                add("implementation", project(":core:common"))
                add("implementation", project(":core:navigation"))
                add("implementation", project(":core:domain"))

                add("testImplementation", project(":core:testing"))
                add("androidTestImplementation", project(":core:testing"))

                add("implementation", libs.findLibrary("androidx.compose.lifecycle.viewmodel").get())
                add("implementation", libs.findLibrary("androidx.compose.lifecycle.runtime").get())

                add("implementation", libs.findLibrary("hilt.navigation").get())
                add("implementation", libs.findLibrary("hilt.android").get())
                add("kapt", libs.findLibrary("hilt.compiler").get())
            }
        }
    }
}