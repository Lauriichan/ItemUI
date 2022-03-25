package me.lauriichan.minecraft.itemui.inventory.handle;

import static java.lang.Math.max;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.lauriichan.minecraft.itemui.inventory.item.ItemEditor;

public interface ItemStorage<E extends ItemStorage<E>> {

    int size();

    ItemStack get(int id);

    default ItemStack getStack(final int id) {
        return get(max(id, 0));
    }

    default ItemStack getStack(final int row, final int column) {
        return get(TableMath.getId(row, column));
    }

    default ItemStack getStack(final int row, final int column, final int rowSize) {
        return get(TableMath.getId(row, column, rowSize));
    }

    default ItemEditor getEditor(final int id) {
        return ItemEditor.of(getStack(id));
    }

    default ItemEditor getEditor(final int row, final int column) {
        return ItemEditor.of(getStack(row, column));
    }

    default ItemEditor getEditor(final int row, final int column, final int rowSize) {
        return ItemEditor.of(getStack(row, column, rowSize));
    }

    E clear();

    E set(int id, ItemStack stack);

    E me();

    default E setStack(final int id, final ItemStack stack) {
        return set(max(id, 0), stack);
    }

    default E setStack(final int row, final int column, final ItemStack stack) {
        return set(TableMath.getId(row, column), stack);
    }

    default E setStack(final int row, final int column, final int rowSize, final ItemStack stack) {
        return set(TableMath.getId(row, column, rowSize), stack);
    }

    default E setEditor(final int id, final ItemEditor editor) {
        if (editor == null) {
            return setStack(id, null);
        }
        return setStack(id, editor.asItemStack());
    }

    default E setEditor(final int row, final int column, final ItemEditor editor) {
        if (editor == null) {
            return setStack(row, column, null);
        }
        return setStack(row, column, editor.asItemStack());
    }

    default E setEditor(final int row, final int column, final int rowSize, final ItemEditor editor) {
        if (editor == null) {
            return setStack(row, column, rowSize, null);
        }
        return setStack(row, column, rowSize, editor.asItemStack());
    }

    default E fillStack(final ItemStack stack) {
        if (stack == null) {
            return clear();
        }
        return fillStack(0, size(), stack);
    }

    default E fillStack(final int start, final ItemStack stack) {
        return fillStack(start, size(), stack);
    }

    default E fillStack(final int start, final int end, final ItemStack stack) {
        if (start < 0 || end < 0 || end < start) {
            throw new IllegalStateException("Range is invalid");
        }
        for (int index = start; index < end; index++) {
            set(index, stack);
        }
        return me();
    }

    default E fillEditor(final ItemEditor editor) {
        if (editor == null) {
            return fillStack(null);
        }
        return fillStack(editor.asItemStack());
    }

    default E fillEditor(final int end, final ItemEditor editor) {
        if (editor == null) {
            return fillStack(end, null);
        }
        return fillStack(end, editor.asItemStack());
    }

    default E fillEditor(final int start, final int end, final ItemEditor editor) {
        if (editor == null) {
            return fillStack(start, end, null);
        }
        return fillStack(start, end, editor.asItemStack());
    }

    default HashMap<Integer, ItemStack> search(final ItemStack stack) {
        final HashMap<Integer, ItemStack> map = new HashMap<>();
        final int size = size();
        for (int index = 0; index < size; index++) {
            final ItemStack current = get(index);
            if (current == null || !current.isSimilar(stack)) {
                continue;
            }
            map.put(index, current);
        }
        return map;
    }

    default HashMap<Integer, ItemStack> possible(final ItemStack stack) {
        final HashMap<Integer, ItemStack> map = new HashMap<>();
        final int size = size();
        for (int index = 0; index < size; index++) {
            final ItemStack current = get(index);
            if (current != null && !current.isSimilar(stack) && current.getType() != Material.AIR) {
                continue;
            }
            map.put(index, current);
        }
        return map;
    }

    default int count(final ItemStack stack) {
        int amount = 0;
        for (final ItemStack current : search(stack).values()) {
            amount += current.getAmount();
        }
        return amount;
    }

}
