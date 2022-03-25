package me.lauriichan.minecraft.itemui.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.lauriichan.minecraft.itemui.inventory.handle.PlayerInventoryHolder;

public final class JoinQuitListener implements Listener {

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        PlayerInventoryHolder.HOLDER.free(event.getPlayer());
    }

}
