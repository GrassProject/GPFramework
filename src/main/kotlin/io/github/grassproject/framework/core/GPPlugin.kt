package io.github.grassproject.framework.core

import io.github.grassproject.framework.core.command.GPCommand
import io.github.grassproject.framework.core.listener.GPListener
import io.github.grassproject.framework.util.bukkit.MinecraftVersion
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.permissions.PermissionDefault
import org.bukkit.plugin.java.JavaPlugin

abstract class GPPlugin : JavaPlugin() {

//    TODO 차후 신설
//    val configManager: Config by lazy { Config(this) }
//    val configYaml: YamlConfiguration
//        get() = configManager.loadYaml("config.yml")
//
//    protected open fun setupConfig() {
//        configManager.createFromResource("config.yml")
//    }

    private val listeners = mutableSetOf<GPListener<out GPPlugin>>()
    private val commands = mutableSetOf<GPCommand<out GPPlugin>>()

    lateinit var framework: IFramework
        private set
    lateinit var plugin: GPPlugin
        private set
    lateinit var adventure: BukkitAudiences
        private set

    override fun onLoad() {
        framework = Framework
        load()
    }

    override fun onEnable() {
        if (!MinecraftVersion.V1_21_1.isAbove()) {
            logger.warning("서버 버전이 너무 낮습니다. 1.21.1 이상을 사용하세요.")
            server.pluginManager.disablePlugin(this)
            return
        }

        plugin = this
        adventure = BukkitAudiences.create(this)

        registerPermission("${plugin.name}.reload", PermissionDefault.OP)
        enable()
        framework.loadPlugin(this)
    }

    override fun onDisable() {
        disable()
        framework.unloadPlugin(this)
    }

    protected open fun registerEvent(listener: GPListener<out GPPlugin>) {
        listeners.add(listener)
        Bukkit.getPluginManager().registerEvents(listener, this)
    }

    fun registerEvents() {
        listeners.forEach { Bukkit.getPluginManager().registerEvents(it, this) }
    }

    fun unregisterEvents() {
        HandlerList.getHandlerLists().forEach { it.unregister(this) }
    }

    protected fun registerCommand(command: GPCommand<out GPPlugin>, reload: Boolean = false) {
        commands.add(command)
        framework.registerCommand(command, reload)
    }

    fun registerPermission(permission: String, permissionDefault: PermissionDefault) {
        framework.registerPermission(permission, permissionDefault)
    }

    fun setupBStats(id: Int) {
        Metrics(this, id)
    }

    protected open fun load() {}
    protected open fun enable() {}
    protected open fun disable() {}
}