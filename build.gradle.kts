import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.1.21"
    id("com.gradleup.shadow") version  "9.0.0-beta10"
}

group = "io.github.grassproject"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") // Paper
    maven("https://maven.devs.beer/")
    maven("https://repo.nexomc.com/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT") // Paper
    compileOnly("dev.lone:api-itemsadder:4.0.10")
    compileOnly("com.nexomc:nexo:1.8.0")

    compileOnly(fileTree("lib") {
        include("*.jar")
    })

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.withType<ShadowJar> {
    exclude("kotlin/**")
    exclude("org/**")

//    relocate("dev.jorel.commandapi", "com.github.soldam.lib.commandapi")
//
//    manifest {
//        attributes["paperweight-mappings-namespace"] = "mojang"
//    }

    archiveFileName.set("${rootProject.name}-${rootProject.version}.jar")
    // destinationDirectory=file("C:\\Users\\aa010\\Desktop\\SoldamPlugin\\plugins")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}