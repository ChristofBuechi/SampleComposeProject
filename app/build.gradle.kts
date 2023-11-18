plugins {
    kotlin("jvm")
    // https://github.com/JetBrains/compose-jb/releases
    id("org.jetbrains.compose") version "1.5.10"
}

dependencies {
    implementation(compose.desktop.currentOs)
}

compose.desktop.application.mainClass = "com.medium.MainKt"
