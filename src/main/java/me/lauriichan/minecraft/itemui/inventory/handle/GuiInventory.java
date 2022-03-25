package me.lauriichan.minecraft.itemui.inventory.handle;

import static java.lang.Math.min;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import me.lauriichan.minecraft.itemui.util.ColorString;
import me.lauriichan.minecraft.itemui.util.Reference;
import me.lauriichan.minecraft.itemui.util.properties.IProperties;

public final class GuiInventory implements InventoryHolder, ItemStorage<GuiInventory> {

    private final IProperties properties;

    private final Reference<Inventory> inventory = Reference.of();

    private int size;
    private String name;
    private InventoryType type;

    private GuiHandler handler;

    private boolean inventoryChanged = false;

    protected GuiInventory(final GuiBuilder builder) {
        this.properties = builder.properties();
        this.handler = Objects.requireNonNull(builder.handler());
        if (!builder.isInventoryValid()) {
            throw new IllegalStateException("Configured inventory is invalid");
        }
        this.name = builder.nameAsString();
        this.size = builder.size();
        this.type = builder.type();
        updateInventory(false);
        if (inventory.get().getHolder() != this) {
            throw new IllegalStateException("InventoryHolder isn't GuiInventory?");
        }
        handler.onInit(this);
    }

    public GuiInventory open(final HumanEntity entity) {
        if (inventory.isEmpty()) {
            return this;
        }
        entity.openInventory(inventory.get());
        return this;
    }

    /*
     * Update
     */

    public GuiInventory updateHandler(final GuiHandler handler) {
        this.handler = Objects.requireNonNull(handler);
        handler.onInit(this);
        return this;
    }

    public GuiInventory updateType(final int size) {
        if (size % 9 != 0) {
            return this;
        }
        this.type = InventoryType.CHEST;
        this.size = Math.max(size, 9);
        return updateInventory(true);
    }

    public GuiInventory updateType(final InventoryType type) {
        this.type = Objects.requireNonNull(type);
        return updateInventory(true);
    }

    public GuiInventory updateName(final ColorString name) {
        return updateName(name.asColoredString());
    }

    public GuiInventory updateName(final String name) {
        this.name = Objects.requireNonNull(name);
        return updateInventory(true);
    }

    private GuiInventory updateInventory(final boolean trigger) {
        if (type == InventoryType.CHEST) {
            inventory.set(Bukkit.createInventory(this, size, name));
        } else {
            inventory.set(Bukkit.createInventory(this, type, name));
        }
        size = inventory.get().getSize();
        if (trigger) {
            inventoryChanged = true;
            update();
            inventoryChanged = false;
        }
        return this;
    }

    public GuiInventory update() {
        handler.onUpdate(this);
        return this;
    }

    /*
     * Getter
     */

    public boolean isInventoryChanged() {
        return inventoryChanged;
    }

    public String getName() {
        return name;
    }

    public IProperties getProperties() {
        return properties;
    }

    public GuiHandler getHandler() {
        return handler;
    }

    @Override
    public Inventory getInventory() {
        return inventory.get();
    }

    @Override
    public int size() {
        return size;
    }

    /*
     * ItemStorage implementation
     */

    @Override
    public GuiInventory me() {
        return this;
    }

    @Override
    public ItemStack get(final int id) {
        return inventory.get().getItem(min(id, size));
    }

    @Override
    public GuiInventory set(final int id, final ItemStack stack) {
        inventory.get().setItem(min(id, size), stack);
        return this;
    }

    @Override
    public GuiInventory clear() {
        inventory.get().clear();
        return this;
    }

}
