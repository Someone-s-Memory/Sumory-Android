import java.io.FileInputStream
import java.util.Properties

plugins {
    id("sumory.android.core")
    id("sumory.android.hilt")
    id("com.google.devtools.ksp")
}

android {
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            type = "String",
            name = "BASE_URL",
            value = "\"${getApiKey("BASE_URL")}\"" // ✅ 문자열로 감싸기
        )
    }

    namespace = "com.sumory.network"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))


    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
    ksp(libs.retrofit.moshi.codegen)
}

fun getApiKey(propertyKey: String) : String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey) ?: throw IllegalArgumentException("Property $propertyKey not found in local.properties")
}