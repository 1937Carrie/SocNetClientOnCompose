plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json
    api(libs.kotlinx.serialization.json)

    // https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
    api(libs.retrofit)
    // Retrofit with Kotlin serialization Converter
    // https://mvnrepository.com/artifact/com.jakewharton.retrofit/retrofit2-kotlinx-serialization-converter
    api(libs.retrofit2.kotlinx.serialization.converter)
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation(libs.okhttp)
    api(libs.logging.interceptor)
}