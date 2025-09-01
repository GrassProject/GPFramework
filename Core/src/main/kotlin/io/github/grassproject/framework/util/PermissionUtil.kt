package io.github.grassproject.framework.util

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault

object PermissionUtil {

    fun register(permission: String, permissionDefault: PermissionDefault = PermissionDefault.OP) {
        Bukkit.getPluginManager().addPermission(Permission(permission, permissionDefault))
    }

}