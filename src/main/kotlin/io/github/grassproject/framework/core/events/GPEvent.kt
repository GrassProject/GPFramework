package io.github.grassproject.framework.core.events

import io.github.grassproject.framework.core.GPPlugin
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

open class GPEvent(
    val name:String,
    isAsync:Boolean=false
):Event(isAsync), Cancellable {
    private var cancelled:Boolean=false;
    override fun isCancelled(): Boolean = cancelled
    override fun setCancelled(cancel: Boolean) {
        this.cancelled=cancel
    }

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
): org.bukkit.event.player.PlayerEvent(player, isAsync), Cancellable {
    private var cancelled:Boolean=false;
    override fun isCancelled(): Boolean = cancelled
    override fun setCancelled(cancel: Boolean) {
        this.cancelled=cancel
    }

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