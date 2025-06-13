plugins {
    id("sumory.jvm.library")
}

dependencies {
    implementation(libs.kotlinx.datetime)
    testImplementation(libs.junit.junit)
}