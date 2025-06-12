plugins {
    id("sumory.android.core")
    id("sumory.android.compose")
    id("sumory.android.lint")
}

android {
    namespace = "com.sumory.design-system"
}

dependencies {
    implementation(libs.coil.kt)
}