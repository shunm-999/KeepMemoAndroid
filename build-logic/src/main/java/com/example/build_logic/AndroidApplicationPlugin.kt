package com.example.build_logic

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.example.build_logic.ktx.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")

                extensions.configure<BaseAppModuleExtension> {
                    configureKotlinAndroid(this)
                    defaultConfig.targetSdk = 32

                    buildFeatures {
                        buildConfig = true
                    }
                }
            }
        }
    }
}