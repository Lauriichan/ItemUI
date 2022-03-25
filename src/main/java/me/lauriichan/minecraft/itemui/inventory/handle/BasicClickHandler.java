package me.lauriichan.minecraft.itemui.inventory.handle;

import java.util.HashMap;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface BasicClickHandler {

    default boolean handleClickAction(final GuiInventory inventory, final InventoryClickEvent event) {
        final InventoryAction action = event.getAction();
        final int slot = event.getSlot();
        ItemStack stack;
        switch (action) {
        case DROP_ALL_SLOT:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            return onDrop(event.getWhoClicked(), inventory, stack = inventory.get(slot), slot, stack.getAmount());
        case DROP_ONE_SLOT:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            return onDrop(event.getWhoClicked(), inventory, inventory.get(slot), slot, 1);
        case PICKUP_ALL:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            return onPickup(event.getWhoClicked(), inventory, stack = inventory.get(slot), slot, stack.getAmount(), true);
        case PICKUP_HALF:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            return onPickup(event.getWhoClicked(), inventory, stack = inventory.get(slot), slot, stack.getAmount() / 2, true);
        case PICKUP_ONE:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            return onPickup(event.getWhoClicked(), inventory, stack = inventory.get(slot), slot, 1, true);
        case PICKUP_SOME:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            int amount = event.getCursor().getMaxStackSize() - event.getCursor().getAmount();
            if (amount > (stack = inventory.get(slot)).getAmount()) {
                amount = stack.getAmount();
            }
            return onPickup(event.getWhoClicked(), inventory, stack, slot, amount, true);
        case PLACE_ALL:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            return onPlace(event.getWhoClicked(), inventory, event.getCursor(), slot, event.getCursor().getAmount());
        case PLACE_ONE:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            return onPlace(event.getWhoClicked(), inventory, event.getCursor(), slot, 1);
        case PLACE_SOME:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            return onPlace(event.getWhoClicked(), inventory, event.getCursor(), slot,
                (stack = inventory.get(slot)).getMaxStackSize() - inventory.get(slot).getAmount());
        case SWAP_WITH_CURSOR:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            return onSwap(event.getWhoClicked(), inventory, inventory.get(slot), event.getCursor(), slot);
        case HOTBAR_SWAP:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            return onSwap(event.getWhoClicked(), inventory, inventory.get(slot), event.getCurrentItem(), slot);
        case MOVE_TO_OTHER_INVENTORY:
            if (event.getClickedInventory() == inventory.getInventory()) {
                return onPickup(event.getWhoClicked(), inventory, event.getCurrentItem(), event.getSlot(),
                    event.getCurrentItem().getAmount(), false);
            } else {
                return onMove(event.getWhoClicked(), inventory, inventory.possible(event.getCurrentItem()), event.getCurrentItem(),
                    event.getCurrentItem().getAmount());
            }
        case CLONE_STACK:
            if (event.getClickedInventory() != inventory.getInventory()) {
                return false;
            }
            return onClone(event.getWhoClicked(), inventory, event.getCurrentItem(), slot);
        case COLLECT_TO_CURSOR:
            return true;
        default:
            return false;
        }
    }

    default boolean onClone(final HumanEntity entity, final GuiInventory inventory, final ItemStack item, final int slot) {
        return false;
    }

    default boolean onMove(final HumanEntity entity, final GuiInventory inventory, final HashMap<Integer, ItemStack> slots,
        final ItemStack item, final int amount) {
        return true;
    }

    default boolean onPickup(final HumanEntity entity, final GuiInventory inventory, final ItemStack item, final int slot, final int amount,
        final boolean cursor) {
        return true;
    }

    default boolean onPlace(final HumanEntity entity, final GuiInventory inventory, final ItemStack item, final int slot,
        final int amount) {
        return true;
    }

    default boolean onSwap(final HumanEntity entity, final GuiInventory inventory, final ItemStack previous, final ItemStack now,
        final int slot) {
        return true;
    }

    default boolean onDrop(final HumanEntity entity, final GuiInventory inventory, final ItemStack item, final int slot, final int amount) {
        return true;
    }

}
