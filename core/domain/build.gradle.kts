plugins {
    id("sumory.android.core")
    id("sumory.android.hilt")
}

android {
    namespace = "com.sumory.domain"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))

    implementation(project(":core:data"))
    implementation(project(":core:model"))


}