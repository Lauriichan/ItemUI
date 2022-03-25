package me.lauriichan.minecraft.itemui.compatibility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;

import me.lauriichan.minecraft.itemui.util.JavaAccessor;

public final class NbtProvider {

    public static final NbtProvider PROVIDER = new NbtProvider();

    private final Class<?> nmsNBTTagCompoundClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagCompound");
    private final Class<?> nmsItemStackClass = JavaAccessor.getClass("net.minecraft.world.item.ItemStack");
    private final Class<?> cbCraftItemStackClass = JavaAccessor.getClass(VersionConstant.craftClassPath("inventory.CraftItemStack"));

    private final Method nbtToItem = Objects
        .requireNonNull(JavaAccessor.getStaticMethod(nmsItemStackClass, nmsItemStackClass, nmsNBTTagCompoundClass));
    private final Method itemToNbt = Objects
        .requireNonNull(JavaAccessor.getDeclaredMethod(nmsItemStackClass, nmsNBTTagCompoundClass, nmsNBTTagCompoundClass));

    private final Constructor<?> constructNbt = Objects.requireNonNull(JavaAccessor.getConstructor(nmsNBTTagCompoundClass));

    private final Method nmsItemToCbItem = Objects
        .requireNonNull(JavaAccessor.getMethod(cbCraftItemStackClass, "asCraftMirror", nmsItemStackClass));
    private final Method cbItemToNmsItem = Objects
        .requireNonNull(JavaAccessor.getMethod(cbCraftItemStackClass, "asNMSCopy", ItemStack.class));

    public static NbtCompound itemStackToNbt(final ItemStack itemStack) {
        return (NbtCompound) NbtConverter.convert(PROVIDER.saveMinecraftStackImpl(PROVIDER.toMinecraftStackImpl(itemStack)));
    }

    public static ItemStack itemStackFromNbt(final NbtCompound compound) {
        return PROVIDER.fromMinecraftStackImpl(PROVIDER.loadMinecraftStackImpl(NbtConverter.convert(compound)));
    }

    private NbtProvider() {}

    public Object saveMinecraftStackImpl(final Object object) {
        if (object == null) {
            return null;
        }
        final Object tag = JavaAccessor.instance(constructNbt);
        return JavaAccessor.invoke(object, itemToNbt, tag);
    }

    public Object loadMinecraftStackImpl(final Object object) {
        if (object == null) {
            return null;
        }
        return JavaAccessor.invokeStatic(nbtToItem, object);
    }

    public ItemStack fromMinecraftStackImpl(final Object object) {
        if (object == null) {
            return null;
        }
        return (ItemStack) JavaAccessor.invokeStatic(nmsItemToCbItem, object);
    }

    public Object toMinecraftStackImpl(final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        return JavaAccessor.invokeStatic(cbItemToNmsItem, itemStack);
    }

}
