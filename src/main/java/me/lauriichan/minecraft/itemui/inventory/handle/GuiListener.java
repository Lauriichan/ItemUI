package me.lauriichan.minecraft.itemui.inventory.handle;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public final class GuiListener implements Listener {

    private final Plugin plugin;

    public GuiListener(final Plugin plugin) {
        this.plugin = plugin;
    }

    private GuiInventory getGui(final Inventory inventory) {
        if (inventory.getHolder() instanceof GuiInventory) {
            return (GuiInventory) inventory.getHolder();
        }
        return null;
    }

    @EventHandler
    public void onEvent(final PrepareAnvilEvent event) {
        final GuiInventory inventory = getGui(event.getInventory());
        if (inventory == null) {
            return;
        }
        inventory.getHandler().onPreAnvil(inventory, event);
    }

    @EventHandler
    public void onEvent(final PrepareItemCraftEvent event) {
        final GuiInventory inventory = getGui(event.getInventory());
        if (inventory == null) {
            return;
        }
        inventory.getHandler().onPreCraft(inventory, event);
    }

    @EventHandler
    public void onEvent(final InventoryCloseEvent event) {
        final GuiInventory inventory = getGui(event.getInventory());
        if (inventory == null) {
            return;
        }
        if (inventory.getHandler().onClose(inventory, event)) {
            Bukkit.getScheduler().runTask(plugin, () -> event.getPlayer().openInventory(inventory.getInventory()));
        }
    }

    @EventHandler
    public void onEvent(final InventoryOpenEvent event) {
        final GuiInventory inventory = getGui(event.getInventory());
        if (inventory == null) {
            return;
        }
        event.setCancelled(inventory.getHandler().onOpen(inventory, event));
    }

    @EventHandler
    public void onEvent(final PrepareItemEnchantEvent event) {
        final GuiInventory inventory = getGui(event.getInventory());
        if (inventory == null) {
            return;
        }
        event.setCancelled(inventory.getHandler().onPreEnchant(inventory, event));
    }

    @EventHandler
    public void onEvent(final EnchantItemEvent event) {
        final GuiInventory inventory = getGui(event.getInventory());
        if (inventory == null) {
            return;
        }
        event.setCancelled(inventory.getHandler().onEnchant(inventory, event));
    }

    @EventHandler
    public void onEvent(final InventoryClickEvent event) {
        final GuiInventory inventory = getGui(event.getInventory());
        if (inventory == null || event instanceof CraftItemEvent || event instanceof InventoryCreativeEvent) {
            return;
        }
        event.setCancelled(inventory.getHandler().onClick(inventory, event));
    }

    @EventHandler
    public void onEvent(final CraftItemEvent event) {
        final GuiInventory inventory = getGui(event.getInventory());
        if (inventory == null) {
            return;
        }
        event.setCancelled(inventory.getHandler().onCraft(inventory, event));
    }

    @EventHandler
    public void onEvent(final InventoryCreativeEvent event) {
        final GuiInventory inventory = getGui(event.getInventory());
        if (inventory == null) {
            return;
        }
        event.setCancelled(inventory.getHandler().onCreative(inventory, event));
    }

    @EventHandler
    public void onEvent(final InventoryDragEvent event) {
        final GuiInventory inventory = getGui(event.getInventory());
        if (inventory == null) {
            return;
        }
        event.setCancelled(inventory.getHandler().onDrag(inventory, event));
    }

    @EventHandler
    public void onEvent(final InventoryMoveItemEvent event) {
        final GuiInventory initiator = getGui(event.getInitiator());
        if (initiator != null) {
            initiator.getHandler().onItemMove(initiator, event, MoveInventory.of(true, event.getSource() == event.getInitiator()));
        }
        final GuiInventory destination = getGui(event.getDestination());
        if (destination != null) {
            destination.getHandler().onItemMove(destination, event, MoveInventory.of(false, event.getSource() == event.getDestination()));
        }
    }

}
