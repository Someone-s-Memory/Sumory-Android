package com.sumory.convention

import com.android.build.api.dsl.ApplicationExtension
import com.sonchan.convention.configureKotlinAndroid
import com.sumory.convention.com.sonchan.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("sumory.android.lint")
        }

        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)

            defaultConfig {
                applicationId = "com.sumory.app"
                minSdk = 26
                targetSdk = 35
                versionCode = 1
                versionName = "1.0.0"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                vectorDrawables.useSupportLibrary = true
            }

            buildFeatures.compose = true

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    isDebuggable = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            composeOptions {
                kotlinCompilerExtensionVersion =
                    libs.findVersion("androidxComposeCompiler").get().toString()
            }

            dependencies {
                add("implementation", libs.findBundle("kotlinx-coroutines").get())
                add("implementation", libs.findBundle("compose").get())
            }
        }
    }
}
