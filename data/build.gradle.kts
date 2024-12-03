plugins {
    alias(libs.plugins.calyr.jvm.library)
}

dependencies {
    implementation(project(":domain"))

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito)
    testImplementation(libs.test.coroutines)
}