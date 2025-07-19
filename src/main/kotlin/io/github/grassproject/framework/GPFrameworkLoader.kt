package io.github.grassproject.framework

import io.papermc.paper.plugin.loader.PluginClasspathBuilder
import io.papermc.paper.plugin.loader.PluginLoader
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver
import org.eclipse.aether.artifact.DefaultArtifact
import org.eclipse.aether.graph.Dependency
import org.eclipse.aether.repository.RemoteRepository

class GPFrameworkLoader : PluginLoader {
    override fun classloader(classpathBuilder: PluginClasspathBuilder) {
        val resolver = MavenLibraryResolver()
        try {
            resolver.addRepository(RemoteRepository.Builder("maven", "default", MavenLibraryResolver.MAVEN_CENTRAL_DEFAULT_MIRROR).build())
        } catch (error: NoSuchFieldError) {
            resolver.addRepository(RemoteRepository.Builder("maven", "default", "https://maven-central.storage-download.googleapis.com/maven2").build())
        }

        resolver.addDependency(Dependency(DefaultArtifact("com.zaxxer:HikariCP:6.0.0"), null))
        // resolver.addDependency(Dependency(DefaultArtifact("com.example:example:version"), null))
        classpathBuilder.addLibrary(resolver)

    }
}