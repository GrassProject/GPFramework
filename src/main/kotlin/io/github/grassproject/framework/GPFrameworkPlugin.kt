package io.github.grassproject.framework

import com.nexomc.nexo.glyphs.RequiredGlyph.id
import io.github.grassproject.framework.api.test.GPPlugin

class GPFrameworkPlugin : GPPlugin() {

    companion object {
        lateinit var instance: GPFrameworkPlugin
            private set
    }

    override fun load() {
        instance = this
    }

    override fun enable() {
        setupBStats(26535)
    }

    override fun disable() {}

}