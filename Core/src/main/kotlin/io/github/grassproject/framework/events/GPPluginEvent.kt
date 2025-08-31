package io.github.grassproject.framework.events

import io.github.grassproject.framework.core.events.GPEvent
import org.bukkit.plugin.Plugin

class GPPluginRegisterEvent(
    val plugin: Plugin
): GPEvent("GPPluginRegisterEvent")

class GPPluginUnregisterEvent(
    val plugin: Plugin
): GPEvent("GPPluginUnregisterEvent")