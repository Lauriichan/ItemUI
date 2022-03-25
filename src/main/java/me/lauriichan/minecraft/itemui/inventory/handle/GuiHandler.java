package me.lauriichan.minecraft.itemui.inventory.handle;

import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public interface GuiHandler {

    default void onInit(final GuiInventory inventory) {}

    default void onPreAnvil(final GuiInventory inventory, final PrepareAnvilEvent event) {}

    default void onPreCraft(final GuiInventory inventory, final PrepareItemCraftEvent event) {}

    default boolean onClose(final GuiInventory inventory, final InventoryCloseEvent event) {
        return false;
    }

    default boolean onOpen(final GuiInventory inventory, final InventoryOpenEvent event) {
        return false;
    }

    default boolean onPreEnchant(final GuiInventory inventory, final PrepareItemEnchantEvent event) {
        return false;
    }

    default boolean onEnchant(final GuiInventory inventory, final EnchantItemEvent event) {
        return false;
    }

    default boolean onClick(final GuiInventory inventory, final InventoryClickEvent event) {
        return false;
    }

    default boolean onCraft(final GuiInventory inventory, final CraftItemEvent event) {
        return false;
    }

    default boolean onCreative(final GuiInventory inventory, final InventoryCreativeEvent event) {
        return false;
    }

    default boolean onDrag(final GuiInventory inventory, final InventoryDragEvent event) {
        return false;
    }

    default boolean onItemMove(final GuiInventory inventory, final InventoryMoveItemEvent event, final MoveInventory state) {
        return false;
    }

    default void onUpdate(final GuiInventory inventory) {

    }

}
