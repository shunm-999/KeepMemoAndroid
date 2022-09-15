package com.example.build_logic

import com.android.build.gradle.TestExtension
import com.example.build_logic.ktx.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidTestPlugin : Plugin<Project> {
    override fun apply(target: org.gradle.api.Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 31
            }
        }
    }
}