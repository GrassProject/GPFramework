plugins {
    kotlin("jvm")
}

group = "io.github.grassproject"
version = parent!!.version

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven("https://maven.devs.beer/")
    maven("https://repo.nexomc.com/releases")
    maven("https://repo.momirealms.net/releases/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    compileOnly("dev.lone:api-itemsadder:4.0.10")
    compileOnly("com.nexomc:nexo:1.10.0")
    compileOnly("net.momirealms:craft-engine-core:0.0.61")
    compileOnly("net.momirealms:craft-engine-bukkit:0.0.61")

    compileOnly(fileTree("lib") {
        include("*.jar")
    })
}

kotlin {
    jvmToolchain(21)
}
