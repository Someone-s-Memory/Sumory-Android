plugins {
    id("sumory.android.application")
    id("sumory.android.hilt")
}

android {
    namespace = "com.sonchan.sumory"
    compileSdk = 35

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
        }
    }
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))

    implementation(project(":core:design-system"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:ui"))

    implementation(project(":feature:home"))

    implementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    implementation(libs.app.update.ktx)
}