import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.2.0"
    java
    id("com.gradleup.shadow") version "9.0.0-beta11"
}

group = "io.github.grassproject"
version = "0.1-RC1"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    implementation(project(":core"))

    implementation("org.jetbrains.exposed:exposed-core:1.0.0-beta-5")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.0.0-beta-5")
    implementation("org.jetbrains.exposed:exposed-dao:1.0.0-beta-5") // Optional
}

kotlin {
    jvmToolchain(21)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.register<ShadowJar>("shadowJarPlugin") {
    archiveFileName.set("GPFramework-${project.version}-shaded.jar")

    from(sourceSets.main.get().output)
    configurations = listOf(project.configurations.runtimeClasspath.get())

    exclude("com/zaxxer/**")
    exclude("kotlin/**", "kotlinx/**")
    exclude("org/intellij/**")
    exclude("org/jetbrains/**")
    exclude("org/slf4j/**")

    relocate("org.bstats", "io.github.grassproject.framework.shadow.bstats")

    exclude(
        "META-INF/*.SF",
        "META-INF/*.DSA",
        "META-INF/*.RSA",
    )
}

tasks {
    build {
        dependsOn(named("shadowJarPlugin"))
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("paper-plugin.yml") {
            expand(getProperties() + mapOf("version" to project.version))
        }
    }
}
