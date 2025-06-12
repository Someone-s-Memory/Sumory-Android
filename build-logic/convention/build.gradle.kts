plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "sumory.android.application"
            implementationClass = "com.sumory.convention.AndroidApplicationConventionPlugin"
        }

        register("androidHilt") {
            id = "sumory.android.hilt"
            implementationClass = "com.sumory.convention.AndroidHiltConventionPlugin"
        }

        register("androidLint") {
            id = "sumory.android.lint"
            implementationClass = "com.sumory.convention.AndroidLintConventionPlugin"
        }

        register("androidCore") {
            id = "sumory.android.core"
            implementationClass = "com.sumory.convention.AndroidCoreConventionPlugin"
        }

        register("androidCompose") {
            id = "sumory.android.compose"
            implementationClass = "com.sumory.convention.AndroidComposeConventionPlugin"
        }

        register("jvmLibrary") {
            id = "sumory.jvm.library"
            implementationClass = "com.sumory.convention.JvmLibraryConventionPlugin"
        }

        register("androidFeature") {
            id = "sumory.android.feature"
            implementationClass = "com.sumory.convention.AndroidFeatureConventionPlugin"
        }
    }
}