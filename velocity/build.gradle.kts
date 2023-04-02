plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.kyori.blossom") version "1.3.0"
}

val longVersion: String by rootProject.extra

dependencies {
    implementation(project(":common"))
    compileOnly("com.velocitypowered:velocity-api:3.1.1")

    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")

    compileOnly("org.jetbrains:annotations:20.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

blossom {
    replaceTokenIn("src/main/java/pl/szczurowsky/minecraftsentryreporter/velocity/MSRVelocityPlugin.java")
    replaceToken("@version@", longVersion)
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependsOn("test")
    archiveFileName.set("MinecraftSentryReporter-Velocity v${project.version}.jar")

    exclude("org/intellij/lang/annotations/**")
    exclude("org/jetbrains/annotations/**")
    exclude("META-INF/**")
    exclude("javax/**")

    mergeServiceFiles()
}