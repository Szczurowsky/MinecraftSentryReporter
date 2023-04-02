plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
}

val longVersion: String by rootProject.extra

dependencies {
    implementation(project(":common"))
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")

    compileOnly("org.jetbrains:annotations:20.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

bukkit {
    main = "pl.szczurowsky.minecraftsentryreporter.spigot.MSRSpigotPlugin"
    apiVersion = "1.19"
    name = "MinecraftSentryReporter"
    authors = listOf("Szczurowsky")
    prefix = "MSR"
    version = longVersion
    commands {
        register("msrproduction")
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependsOn("test")
    archiveFileName.set("MinecraftSentryReporter v{${project.version}}.jar")

    exclude("org/intellij/lang/annotations/**")
    exclude("org/jetbrains/annotations/**")
    exclude("META-INF/**")
    exclude("javax/**")

    mergeServiceFiles()
}