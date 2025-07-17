package io.github.grassproject.framework

import io.github.grassproject.framework.api.test.GPPlugin

class GPFrameworkPlugin : GPPlugin() {

    companion object {
        lateinit var instance: GPFrameworkPlugin
            private set
    }

    override fun load() {
        instance = this
    }

    override fun enable() {}

    override fun disable() {}

}