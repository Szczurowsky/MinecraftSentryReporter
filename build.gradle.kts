plugins {
    id("java")
}

allprojects {
    group = "pl.szczurowsky.minecraft-sentry-reporter"
    version = "1.0.0"
    val longVersion by extra { version.toString() + "-" + "git rev-parse --abbrev-ref HEAD".runCommand(workingDir = rootDir) + "." + "git rev-parse --short HEAD".runCommand(workingDir = rootDir) }


    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://storehouse.okaeri.eu/repository/maven-releases/")
    }
}

fun String.runCommand(
    workingDir: File = File("."),
    timeoutAmount: Long = 60,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS
): String = ProcessBuilder(split("\\s(?=(?:[^'\"`]*(['\"`])[^'\"`]*\\1)*[^'\"`]*$)".toRegex()))
    .directory(workingDir)
    .redirectOutput(ProcessBuilder.Redirect.PIPE)
    .redirectError(ProcessBuilder.Redirect.PIPE)
    .start()
    .apply { waitFor(timeoutAmount, timeoutUnit) }
    .run {
        val error = errorStream.bufferedReader().readText().trim()
        inputStream.bufferedReader().readText().trim()
    }