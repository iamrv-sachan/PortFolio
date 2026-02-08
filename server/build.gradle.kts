import java.util.Properties

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
    application
}

group = "com.example.protfolio"
version = "1.0.0"

application {
    mainClass.set("com.example.protfolio.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

/**
 * 🔐 Load mongo.uri from local.properties
 * and expose it as MONGO_URI env variable
 */
tasks.withType<JavaExec>().configureEach {
    val localProps = Properties()
    val file = rootProject.file("local.properties")

    if (file.exists()) {
        file.inputStream().use { localProps.load(it) }
        val mongoUri = localProps.getProperty("mongo.uri")
        if (!mongoUri.isNullOrBlank()) {
            environment("MONGO_URI", mongoUri)
        }
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation(libs.bundles.ktor)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.mongodb.driver)
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}
