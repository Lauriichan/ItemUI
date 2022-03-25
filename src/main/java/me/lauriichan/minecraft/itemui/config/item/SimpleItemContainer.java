package me.lauriichan.minecraft.itemui.config.item;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtDeserializer;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtSerializer;

import me.lauriichan.minecraft.itemui.compatibility.NbtProvider;
import me.lauriichan.minecraft.itemui.config.DataReloadable;
import me.lauriichan.minecraft.itemui.inventory.handle.GuiInventory;
import me.lauriichan.minecraft.itemui.inventory.item.ItemEditor;

public final class SimpleItemContainer extends DataReloadable {

    private final ArrayList<ItemStack> items = new ArrayList<>();
    private final SimpleItemHandler handler = new SimpleItemHandler(this);

    public SimpleItemContainer(File folder) {
        super(new File(folder, "items.dat"), true);
    }

    public SimpleItemHandler getHandler() {
        return handler;
    }

    public int size() {
        return items.size();
    }

    public boolean add(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        for (int index = 0; index < items.size(); index++) {
            ItemStack found = items.get(index);
            if (!itemStack.isSimilar(found)) {
                continue;
            }
            return false;
        }
        try {
            return items.add(ItemEditor.ofClone(itemStack).amount(1).asItemStack());
        } finally {
            updateInventory();
        }
    }

    public boolean remove(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        for (int index = 0; index < items.size(); index++) {
            ItemStack found = items.get(index);
            if (!itemStack.isSimilar(found)) {
                continue;
            }
            try {
                items.remove(index);
            } finally {
                updateInventory();
            }
            return true;
        }
        return false;
    }

    public ItemStack get(int index) {
        if (index < 0 || index > items.size()) {
            return null;
        }
        return items.get(index);
    }

    public void updateInventory() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            Inventory inv = player.getOpenInventory().getTopInventory();
            try {
                if (!(inv.getHolder() instanceof GuiInventory)) {
                    continue;
                }
            } catch (IllegalStateException ignore) {
                continue;
            }
            GuiInventory inventory = (GuiInventory) inv.getHolder();
            if (inventory.getHandler() != handler) {
                continue;
            }
            inventory.update();
        }
    }

    public void closeInventory() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            Inventory inv = player.getOpenInventory().getTopInventory();
            try {
                if (!(inv.getHolder() instanceof GuiInventory)) {
                    continue;
                }
            } catch (IllegalStateException ignore) {
                continue;
            }
            GuiInventory inventory = (GuiInventory) inv.getHolder();
            if (inventory.getHandler() != handler) {
                continue;
            }
            player.closeInventory();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onLoad() throws Throwable {
        NbtNamedTag nbt = NbtDeserializer.COMPRESSED.fromFile(file);
        if (nbt.getTag() == null || nbt.getTag().getType() != NbtType.COMPOUND) {
            throw new IllegalStateException("Invalid nbt");
        }
        NbtCompound data = (NbtCompound) nbt.getTag();
        if (!data.hasKey("items", NbtType.LIST)) {
            throw new IllegalStateException("Invalid nbt");
        }
        NbtList<?> rawList = data.getTagList("items");
        if (rawList.getElementType() != NbtType.COMPOUND) {
            throw new IllegalStateException("Invalid nbt");
        }
        items.clear();
        NbtList<NbtCompound> list = (NbtList<NbtCompound>) rawList;
        if (list.isEmpty()) {
            return;
        }
        for (int index = 0; index < list.size(); index++) {
            ItemStack item = NbtProvider.itemStackFromNbt(list.get(index));
            if (item == null) {
                continue;
            }
            items.add(item);
        }
    }

    @Override
    protected void onSave() throws Throwable {
        NbtList<NbtCompound> list = new NbtList<>();
        for (int index = 0; index < items.size(); index++) {
            NbtCompound item = NbtProvider.itemStackToNbt(items.get(index));
            if (item == null) {
                continue;
            }
            list.add(item);
        }
        NbtCompound data = new NbtCompound();
        data.set("items", list);
        NbtSerializer.COMPRESSED.toFile(new NbtNamedTag("root", data), file);
    }

}
