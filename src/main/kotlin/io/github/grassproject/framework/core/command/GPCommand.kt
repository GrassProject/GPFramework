package io.github.grassproject.framework.core.command

import io.github.grassproject.framework.core.GPPlugin

abstract class GPCommand<T : GPPlugin>(
    val plugin: T,
) {
}