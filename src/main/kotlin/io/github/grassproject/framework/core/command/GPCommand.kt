package io.github.grassproject.framework.core.command

import io.github.grassproject.framework.core.GPPlugin
import org.bukkit.command.Command
import org.bukkit.permissions.PermissionDefault

abstract class GPCommand<T : GPPlugin>(
    val plugin: T,
    val names: List<String>,
    permissionDefault: PermissionDefault = PermissionDefault.TRUE
) : Command(names.first()) {
    
}