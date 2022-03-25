package me.lauriichan.minecraft.itemui.inventory.handle;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public final class PlayerInventoryHolder {

    public static final PlayerInventoryHolder HOLDER = new PlayerInventoryHolder();

    private PlayerInventoryHolder() {}

    private final Function<UUID, GuiInventory> func = id -> new GuiBuilder().size(54).handler(EmptyGuiHandler.INSTANCE).buildInventory();
    private final ConcurrentHashMap<UUID, GuiInventory> inventories = new ConcurrentHashMap<>();

    public GuiInventory getInventory(final HumanEntity player, final GuiHandler handler) {
        return getInventory(player).updateHandler(handler);
    }

    public GuiInventory getInventory(final Player player, final GuiHandler handler) {
        return getInventory((OfflinePlayer) player, handler);
    }

    public GuiInventory getInventory(final OfflinePlayer player, final GuiHandler handler) {
        return getInventory(player).updateHandler(handler);
    }

    public GuiInventory getInventory(final UUID uniqueId, final GuiHandler handler) {
        return getInventory(uniqueId).updateHandler(handler);
    }

    public GuiInventory getInventory(final HumanEntity player) {
        return getInventory(player.getUniqueId());
    }

    public GuiInventory getInventory(final Player player) {
        return getInventory((OfflinePlayer) player);
    }

    public GuiInventory getInventory(final OfflinePlayer player) {
        return getInventory(player.getUniqueId());
    }

    public GuiInventory getInventory(final UUID uniqueId) {
        return inventories.computeIfAbsent(uniqueId, func);
    }

    public GuiInventory getInventoryIfExists(final HumanEntity player) {
        return getInventoryIfExists(player.getUniqueId());
    }

    public GuiInventory getInventoryIfExists(final OfflinePlayer player) {
        return getInventoryIfExists(player.getUniqueId());
    }

    public GuiInventory getInventoryIfExists(final UUID uniqueId) {
        return inventories.get(uniqueId);
    }

    public void free(final OfflinePlayer player) {
        free(player.getUniqueId());
    }

    public void free(final UUID uniqueId) {
        inventories.remove(uniqueId);
    }

}
