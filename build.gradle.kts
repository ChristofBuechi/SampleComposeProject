buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.9.20"))
    }
}

allprojects {
    repositories {
        jcenter()

        // JetPack Compose - Desktop
        // https://github.com/JetBrains/compose-jb
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
