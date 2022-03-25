package me.lauriichan.minecraft.itemui.command;

import org.bukkit.entity.Player;

import com.syntaxphoenix.avinity.command.IPermission;

public enum Permissions implements IPermission {

    COMMAND_ITEMS_ADD,
    COMMAND_ITEMS_GET,
    COMMAND_ITEMS_REMOVE,
    COMMAND_ITEMS_INDEX,
    COMMAND_ITEMS_SAVE;

    private final String id = "itemui." + name().toLowerCase().replace('_', '.');

    @Override
    public String id() {
        return id;
    }

    public boolean isPermitted(Player player) {
        return player.hasPermission(id);
    }

}
