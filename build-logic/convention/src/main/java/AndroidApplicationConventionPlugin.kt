package com.sumory.convention

import com.android.build.api.dsl.ApplicationExtension
import com.sonchan.convention.configureKotlinAndroid
import com.sumory.convention.com.sonchan.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies


class AndroidApplicationConventionPlugin : Plugin<Project> {

    // Entry point for the plugin
    override fun apply(target: Project) {
        with(target) {
            // Apply necessary plugins for Android and Kotlin
            with(pluginManager) {
                apply("com.android.application") // Apply Android library plugin
                apply("org.jetbrains.kotlin.android") // Apply Kotlin Android plugin
                apply("sumory.android.lint") // Apply custom lint plugin (if available)
            }

            // Configure Android application extension settings
            extensions.configure<ApplicationExtension> {
                // Apply common Kotlin Android settings
                configureKotlinAndroid(this)
                compileSdk = 35

                // Configure default settings for the application
                defaultConfig {
                    applicationId = "com.sumory.build"
                    minSdk = 26
                    targetSdk = 35
                    versionCode = 19
                    versionName = "1.2.8"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    vectorDrawables.useSupportLibrary = true
                }

                // Enable Jetpack Compose feature for the project
                buildFeatures.compose = true

                // Configure build types
                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        isDebuggable = false
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
                }

                // Set Compose compiler extension version from the Version Catalog
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
                }

                // Add necessary dependencies using Version Catalog bundles
                dependencies {
                    add("implementation",libs.findBundle("kotlinx-coroutines").get())
                    add("implementation",libs.findBundle("compose").get())
                }
            }
        }
    }
}