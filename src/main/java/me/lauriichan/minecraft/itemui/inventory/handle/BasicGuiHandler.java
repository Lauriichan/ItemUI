package me.lauriichan.minecraft.itemui.inventory.handle;

import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class BasicGuiHandler implements BasicClickHandler, GuiHandler {

    @Override
    public boolean onClick(final GuiInventory inventory, final InventoryClickEvent event) {
        return handleClickAction(inventory, event);
    }

}
