plugins {
    id("sumory.android.core")
    id("sumory.android.hilt")
}

android {
    namespace = "com.sumory.datastore"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))

    implementation(libs.androidx.dataStore.core)
    implementation(libs.protobuf.kotlin.lite)
}