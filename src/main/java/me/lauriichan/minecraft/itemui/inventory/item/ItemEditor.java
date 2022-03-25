package me.lauriichan.minecraft.itemui.inventory.item;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.lauriichan.minecraft.itemui.compatibility.IHeadProvider;
import me.lauriichan.minecraft.itemui.config.Message;
import me.lauriichan.minecraft.itemui.util.Tuple;

public class ItemEditor {

    public static ItemEditor head(final String texture) {
        return new ItemEditor(Material.PLAYER_HEAD).setTexture(texture);
    }

    public static ItemEditor of(final Material material) {
        return new ItemEditor(material);
    }

    public static ItemEditor of(final ItemStack itemStack) {
        return new ItemEditor(itemStack);
    }

    public static ItemEditor ofClone(final ItemStack itemStack) {
        return new ItemEditor(itemStack.clone());
    }

    protected final ItemStack itemStack;
    protected ItemMeta itemMeta;

    private LoreEditor lore;
    private NameEditor name;

    public ItemEditor(final Material material) {
        this(new ItemStack(material));
    }

    public ItemEditor(final ItemStack stack) {
        this(stack, stack.getItemMeta());
    }

    public ItemEditor(final Material material, final ItemMeta meta) {
        this(new ItemStack(material), meta);
    }

    public ItemEditor(final ItemStack stack, final ItemMeta meta) {
        Objects.requireNonNull(stack);
        this.itemStack = stack;
        this.itemMeta = meta;
    }

    public boolean hasItemMeta() {
        return itemMeta != null;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public ItemEditor updateItemMeta(final Consumer<ItemMeta> consumer) {
        if (itemMeta == null || consumer == null) {
            return this;
        }
        consumer.accept(itemMeta);
        return this;
    }

    public boolean isHead() {
        return hasItemMeta() && itemMeta instanceof SkullMeta;
    }

    public ItemEditor setTexture(final String texture) {
        if (!hasItemMeta()) {
            return this;
        }
        IHeadProvider.provider().setTexture((SkullMeta) itemMeta, texture);
        return this;
    }

    public String getTexture() {
        if (!hasItemMeta()) {
            return null;
        }
        return IHeadProvider.provider().getTexture((SkullMeta) itemMeta);
    }

    public LoreEditor lore() {
        return lore == null ? lore = new LoreEditor(this) : lore;
    }

    public NameEditor name() {
        return name == null ? name = new NameEditor(this) : name;
    }

    public boolean hasLore() {
        return hasItemMeta() ? itemMeta.hasLore() : false;
    }

    public Optional<List<String>> getLore() {
        return Optional.ofNullable(hasLore() ? itemMeta.getLore() : null);
    }

    public ItemEditor setLore(final List<String> lore) {
        return lore().set(lore).apply();
    }

    public ItemEditor setLore(final String lore) {
        return lore().set(lore.split("\n")).apply();
    }

    public ItemEditor setLore(final String... lore) {
        return lore().set(lore).apply();
    }

    @SuppressWarnings("unchecked")
    public ItemEditor setLore(final Message message, final Tuple<String, Object>... placeholders) {
        return setLore(message.asMessageString(placeholders));
    }

    public boolean hasName() {
        return hasItemMeta() ? itemMeta.hasDisplayName() : false;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(hasName() ? itemMeta.getDisplayName() : null);
    }

    public ItemEditor setName(final String name) {
        return name().set(name).apply();
    }

    @SuppressWarnings("unchecked")
    public ItemEditor setName(final Message message, final Tuple<String, Object>... placeholders) {
        return setName(message.asMessageString(placeholders));
    }

    public boolean hasEnchants() {
        if (!hasItemMeta()) {
            return false;
        }
        return itemMeta.hasEnchants();
    }

    public boolean hasEnchant(final Enchantment enchantment) {
        if (!hasItemMeta()) {
            return false;
        }
        return itemMeta.hasEnchant(enchantment);
    }

    public boolean hasEnchantConflict(final Enchantment enchantment) {
        return hasItemMeta() ? itemMeta.hasConflictingEnchant(enchantment) : false;
    }

    public int getEnchant(final Enchantment enchantment) {
        if (!hasItemMeta()) {
            return 0;
        }
        return itemMeta.getEnchantLevel(enchantment);
    }

    public ItemEditor setEnchant(final Enchantment enchantment, final int level) {
        if (!hasItemMeta()) {
            return this;
        }
        if (itemMeta.hasEnchant(enchantment)) {
            itemMeta.removeEnchant(enchantment);
        }
        if (level == 0) {
            return this;
        }
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public Material material() {
        return itemStack.getType();
    }

    public ItemEditor material(final Material material) {
        apply();
        itemStack.setType(material);
        itemMeta = itemStack.getItemMeta();
        return this;
    }

    public int amount() {
        return itemStack.getAmount();
    }

    public ItemEditor amount(final int amount) {
        final int max = material().getMaxStackSize();
        itemStack.setAmount(max < amount ? max : amount < 0 ? 0 : amount);
        return this;
    }

    public boolean hasDurability() {
        return hasItemMeta() ? itemMeta instanceof Damageable ? itemMeta.isUnbreakable() == false : false : false;
    }

    public int getDurability() {
        return hasDurability() ? material().getMaxDurability() - ((Damageable) itemMeta).getDamage() : -1;
    }

    public int getDamage() {
        return hasDurability() ? ((Damageable) itemMeta).getDamage() : 0;
    }

    public ItemEditor setDurability(final int durability) {
        if (!hasDurability()) {
            return this;
        }
        ((Damageable) itemMeta).setDamage(material().getMaxDurability() - durability);
        return this;
    }

    public ItemEditor setDamage(final int damage) {
        if (!hasDurability()) {
            return this;
        }
        ((Damageable) itemMeta).setDamage(damage);
        return this;
    }

    public int getMaxAmount() {
        return material().getMaxStackSize();
    }

    public int getMaxDurability() {
        return material().getMaxDurability();
    }

    public ItemEditor apply() {
        if (hasItemMeta()) {
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemStack asItemStack() {
        if (hasItemMeta()) {
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

}
