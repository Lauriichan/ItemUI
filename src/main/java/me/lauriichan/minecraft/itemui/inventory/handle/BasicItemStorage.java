package me.lauriichan.minecraft.itemui.inventory.handle;

import static java.lang.Math.min;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.inventory.ItemStack;

public class BasicItemStorage implements ItemStorage<BasicItemStorage> {

    private final ConcurrentHashMap<Integer, ItemStack> map = new ConcurrentHashMap<>();
    private final int size;

    public BasicItemStorage(final int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ItemStack get(final int id) {
        return map.get(min(id, size));
    }

    @Override
    public BasicItemStorage set(final int id, final ItemStack stack) {
        map.put(min(id, size), stack);
        return this;
    }

    @Override
    public BasicItemStorage me() {
        return this;
    }

    @Override
    public BasicItemStorage clear() {
        map.clear();
        return this;
    }

}
