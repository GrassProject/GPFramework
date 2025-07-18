package io.github.grassproject.framework.core.listener

import io.github.grassproject.framework.core.GPPlugin
import org.bukkit.event.Listener

// Listener 흠..
abstract class GPListener<T : GPPlugin>(
    val plugin: T,
) : Listener