package io.github.grassproject.gplibrary

import io.github.grassproject.gplibrary.config.Config
import io.github.grassproject.gplibrary.util.Register
// import io.github.grassproject.gplibrary.test.PlayerJoin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class GPLibraryPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: GPLibraryPlugin
            private set
    }

    override fun onEnable() {
        instance = this

        // Register(this).resistEventListener(PlayerJoin())

//        val config = Config(this)
//        val success1 = config.create("data/example.yml")
//        println("example.yml 파일 생성 성공? $success1")
//
//        config.setValue("data/example.yml", "test", 1)

    }
}