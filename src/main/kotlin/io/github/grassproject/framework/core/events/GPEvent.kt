package io.github.grassproject.framework.core.events

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

open class GPEvent(
    val name:String,
    isAsync:Boolean=false
):Event(isAsync) {
    override fun getEventName() = this.name
    override fun getHandlers(): HandlerList = getHandlerList()
    companion object {
        private val handlers = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }
}

open class GPPlayerEvent(
    player: org.bukkit.entity.Player,
    val name:String,
    isAsync:Boolean=false
): org.bukkit.event.player.PlayerEvent(player, isAsync) {
    override fun getEventName() = this.name
    override fun getHandlers(): HandlerList = getHandlerList()
    companion object {
        private val handlers = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }
}