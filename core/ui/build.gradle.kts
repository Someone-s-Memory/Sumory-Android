plugins {
    id("sumory.android.core")
    id("sumory.android.compose")
}

android {
    namespace = "com.sumory.ui"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))
    implementation(project(":core:model"))
    implementation(project(":core:design-system"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.kotlinx.datetime)
    implementation(libs.accompanist.permission)
}