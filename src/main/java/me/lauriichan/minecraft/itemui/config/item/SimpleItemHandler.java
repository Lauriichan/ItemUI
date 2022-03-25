package me.lauriichan.minecraft.itemui.config.item;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import me.lauriichan.minecraft.itemui.config.Messages;
import me.lauriichan.minecraft.itemui.inventory.handle.BasicGuiHandler;
import me.lauriichan.minecraft.itemui.inventory.handle.GuiInventory;
import me.lauriichan.minecraft.itemui.inventory.handle.TableMath;
import me.lauriichan.minecraft.itemui.inventory.item.ItemEditor;
import me.lauriichan.minecraft.itemui.util.InventoryHelper;
import me.lauriichan.minecraft.itemui.util.properties.IProperty;

@SuppressWarnings("unchecked")
public final class SimpleItemHandler extends BasicGuiHandler {

    public static final ItemStack NEXT_PAGE = ItemEditor.head("a4a363afda1922fac8107b6b0a51eb6f6c541575e2da56208f65aecded26315a")
        .setName(Messages.INVENTORY_NAVIGATION_NEXT).asItemStack();
    public static final ItemStack PREVIOUS_PAGE = ItemEditor.head("5c491dbfa372df7999262cbfa8916518ab3e3595bd6bddf9f7d195df3f7885bd")
        .setName(Messages.INVENTORY_NAVIGATION_PREVIOUS).asItemStack();
    public static final ItemStack FIRST_PAGE = ItemEditor.head("1db9b4e23d66659bb8ede584a648830d29e6449179102d4dc1c7fe9b00f8a5e2")
        .setName(Messages.INVENTORY_NAVIGATION_FIRST).asItemStack();
    public static final ItemStack LAST_PAGE = ItemEditor.head("5c6a61e38722a298a9c91e15b12a06eb7b37cfb7afc535422897b6bdf50c76d")
        .setName(Messages.INVENTORY_NAVIGATION_LAST).asItemStack();
    public static final ItemStack INFO_PAGE = ItemEditor.head("54e3e178cebcdc3b8226956f78273d98cb6838667d027b4d6be670af6a1d85f7")
        .setName(Messages.INVENTORY_NAVIGATION_INFO).asItemStack();

    public static final ItemStack PLACEHOLDER = ItemEditor.of(Material.BLACK_STAINED_GLASS_PANE).setName("&r").asItemStack();

    private final SimpleItemContainer container;

    public SimpleItemHandler(final SimpleItemContainer container) {
        this.container = container;
    }

    @Override
    public void onInit(GuiInventory inventory) {
        onUpdate(inventory);
    }

    @Override
    public void onUpdate(GuiInventory inventory) {
        inventory.clear();
        inventory.fillStack(TableMath.getId(4, 0), PLACEHOLDER);
        fillInventory(inventory);
    }

    private void fillInventory(GuiInventory inventory) {
        int page = inventory.getProperties().find("page").cast(Integer.class).getOrDefault(0);
        int maxPage = (int) Math.ceil(container.size() / 36d);
        if (maxPage == 0) {
            inventory.setEditor(5, 4, ItemEditor.ofClone(INFO_PAGE).amount(1));
            return;
        }
        if (page < 0) {
            page = 0;
        } else if (page >= maxPage) {
            page = maxPage - 1;
        }
        int startIndex = page * 36;
        int endIndex = (page + 1) * 36;
        if (endIndex > container.size()) {
            endIndex = container.size();
        }
        int inventoryIndex = 0;
        for (int index = startIndex; index < endIndex; index++) {
            inventory.set(inventoryIndex++, container.get(index));
        }
        inventory.setEditor(5, 4, ItemEditor.ofClone(INFO_PAGE).amount(page + 1));
        if (page > 0) {
            inventory.setEditor(5, 1, ItemEditor.ofClone(PREVIOUS_PAGE).amount(page));
        }
        if (page > 1) {
            inventory.setEditor(5, 2, ItemEditor.ofClone(FIRST_PAGE).amount(1));
        }
        if (page < (maxPage - 2)) {
            inventory.setEditor(5, 7, ItemEditor.ofClone(LAST_PAGE).amount(maxPage));
        }
        if (page < (maxPage - 1)) {
            inventory.setEditor(5, 8, ItemEditor.ofClone(INFO_PAGE).amount(page + 2));
        }
    }

    @Override
    public boolean onClone(HumanEntity entity, GuiInventory inventory, ItemStack item, int slot) {
        container.remove(item);
        return true;
    }

    @Override
    public boolean onPickup(HumanEntity entity, GuiInventory inventory, ItemStack item, int slot, int amount, boolean cursor) {
        if (slot < TableMath.getId(4, 0)) {
            if (item == null || item.getType().name().endsWith("AIR")) {
                return true;
            }
            InventoryHelper.add(entity.getLocation(), entity.getInventory(), item, cursor ? 1 : item.getMaxStackSize());
            return true;
        }
        if (item.getType() != Material.PLAYER_HEAD) {
            return true;
        }
        if (slot == TableMath.getId(5, 1)) {
            inventory.getProperties()
                .set(IProperty.of("page", inventory.getProperties().find("page").cast(Integer.class).getOrDefault(1) - 1));
            inventory.update();
            return true;
        }
        if (slot == TableMath.getId(5, 2)) {
            inventory.getProperties().set(IProperty.of("page", 0));
            inventory.update();
            return true;
        }
        if (slot == TableMath.getId(5, 7)) {
            inventory.getProperties().set(IProperty.of("page", Integer.MAX_VALUE));
            inventory.update();
            return true;
        }
        if (slot == TableMath.getId(5, 8)) {
            inventory.getProperties()
                .set(IProperty.of("page", inventory.getProperties().find("page").cast(Integer.class).getOrDefault(0) + 1));
            inventory.update();
            return true;
        }
        return true;
    }

    @Override
    public boolean onPlace(HumanEntity entity, GuiInventory inventory, ItemStack item, int slot, int amount) {
        container.add(item);
        return true;
    }

    @Override
    public boolean onMove(HumanEntity entity, GuiInventory inventory, HashMap<Integer, ItemStack> slots, ItemStack item, int amount) {
        container.add(item);
        return true;
    }

}
