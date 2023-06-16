plugins {
    id("java")
    id("java-library")
}

dependencies {
    compileOnly("org.apache.logging.log4j:log4j-api:2.20.0")
    compileOnly("org.jetbrains:annotations:24.0.1")
    compileOnly("eu.okaeri:okaeri-configs-core:4.0.9")
    api("eu.okaeri:okaeri-configs-hjson:5.0.0-beta.5")
    api("io.sentry:sentry:6.23.0")
}