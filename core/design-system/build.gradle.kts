plugins {
    id("sumory.android.core")
    id("sumory.android.compose")
    id("sumory.android.lint")
}

android {
    namespace = "com.sumory.design"
}

dependencies {
    implementation(libs.coil.kt)
}