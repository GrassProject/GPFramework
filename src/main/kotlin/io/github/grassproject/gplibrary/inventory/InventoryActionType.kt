package io.github.grassproject.gplibrary.inventory

enum class InventoryActionType {
    NONE,
    COMMAND,
    OPEN_PAGE,
    CLOSE_INVENTORY;

    companion object {
        fun fromString(type: String?): InventoryActionType {
            return entries.find { it.name.equals(type, ignoreCase = true) } ?: NONE
        }
    }
}
