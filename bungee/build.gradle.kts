plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bungee") version "0.5.3"
}

val longVersion: String by rootProject.extra

dependencies {
    implementation(project(":common"))
    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:20.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

bungee {
    main = "com.mcmanhunt.event.proxy.ProxyPlugin"
    name = "MinecraftSentryReporter"
    author = "Szczurowsky"
    version = longVersion
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependsOn("test")
    archiveFileName.set("MinecraftSentryReporter-Bungee v${project.version}.jar")

    exclude("org/intellij/lang/annotations/**")
    exclude("org/jetbrains/annotations/**")
    exclude("META-INF/**")
    exclude("javax/**")

    mergeServiceFiles()
}