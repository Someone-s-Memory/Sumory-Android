plugins {
    id("sumory.android.core")
    id("sumory.android.hilt")
}

android {
    namespace = "com.sumory.data"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)

}