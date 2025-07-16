import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.1.21"
    id("com.gradleup.shadow") version  "9.0.0-beta10"
//    id("xyz.jpenilla.run-paper") version "2.3.1"
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
    compileOnly("com.arcaniax:HeadDatabase-API:1.3.2")

    compileOnly(fileTree("lib") {
        include("*.jar")
    })

    // implementation("org.reflections:reflections:0.10.2")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.withType<ShadowJar> {
    // exclude("kotlin/**")
    exclude("org/**")
    // exclude("META-INF/**")

//    relocate("dev.jorel.commandapi", "com.github.soldam.lib.commandapi")
//
//    manifest {
//        attributes["paperweight-mappings-namespace"] = "mojang"
//    }

    archiveFileName.set("${rootProject.name}-${rootProject.version}.jar")
    destinationDirectory=file("C:\\Users\\aa990\\OneDrive\\바탕 화면\\GPServer\\plugins") //
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

//tasks.runServer {
//        // Configure the Minecraft version for our task.
//        // This is the only required configuration besides applying the plugin.
//        // Your plugin's jar (or shadowJar if present) will be used automatically.
//    minecraftVersion("1.21.1")
//}
