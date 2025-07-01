plugins {
    id("sumory.android.feature")
    id("sumory.android.hilt")
}

android {
    namespace = "com.sumory.diary"
}

dependencies {
    implementation(libs.accompanist.permission)
}
