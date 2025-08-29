plugins {
    kotlin("jvm")
    java
}

group = rootProject.group
version = rootProject.version

val exposed = "1.0.0-beta-5"

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

    implementation("org.bstats:bstats-bukkit:3.1.0")

    compileOnly("com.zaxxer:HikariCP:7.0.2")

    implementation("org.jetbrains.exposed:exposed-core:${exposed}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${exposed}")
    implementation("org.jetbrains.exposed:exposed-dao:${exposed}")
}

kotlin {
    jvmToolchain(21)
}
