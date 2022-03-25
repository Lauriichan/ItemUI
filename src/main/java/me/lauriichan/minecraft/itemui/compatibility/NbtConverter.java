package me.lauriichan.minecraft.itemui.compatibility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.syntaxphoenix.syntaxapi.nbt.*;

import me.lauriichan.minecraft.itemui.util.JavaAccessor;

public final class NbtConverter {

    public static final NbtConverter CONVERTER = new NbtConverter();

    public static NbtTag convert(Object minecraftTag) {
        return CONVERTER.tagFromMinecraft(minecraftTag);
    }

    public static Object convert(NbtTag tag) {
        return CONVERTER.tagToMinecraft(tag);
    }

    private final Class<?> nmsNBTBaseClass = JavaAccessor.getClass("net.minecraft.nbt.NBTBase");

    private final Class<?> nmsNBTTagEndClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagEnd");
    private final Class<?> nmsNBTTagByteClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagByte");
    private final Class<?> nmsNBTTagShortClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagShort");
    private final Class<?> nmsNBTTagIntClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagInt");
    private final Class<?> nmsNBTTagLongClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagLong");
    private final Class<?> nmsNBTTagFloatClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagFloat");
    private final Class<?> nmsNBTTagDoubleClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagDouble");
    private final Class<?> nmsNBTTagStringClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagString");
    private final Class<?> nmsNBTTagByteArrayClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagByteArray");
    private final Class<?> nmsNBTTagIntArrayClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagIntArray");
    private final Class<?> nmsNBTTagLongArrayClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagLongArray");
    private final Class<?> nmsNBTTagListClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagList");
    private final Class<?> nmsNBTTagCompoundClass = JavaAccessor.getClass("net.minecraft.nbt.NBTTagCompound");

    private final Method getType = Objects.requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTBaseClass, byte.class));

    private final Field getEnd = Objects.requireNonNull(JavaAccessor.getStaticField(nmsNBTTagEndClass, nmsNBTTagEndClass));
    private final Method createByte = Objects
        .requireNonNull(JavaAccessor.getStaticMethod(nmsNBTTagByteClass, nmsNBTTagByteClass, byte.class));
    private final Method createShort = Objects
        .requireNonNull(JavaAccessor.getStaticMethod(nmsNBTTagShortClass, nmsNBTTagShortClass, short.class));
    private final Method createInt = Objects.requireNonNull(JavaAccessor.getStaticMethod(nmsNBTTagIntClass, nmsNBTTagIntClass, int.class));
    private final Method createLong = Objects
        .requireNonNull(JavaAccessor.getStaticMethod(nmsNBTTagLongClass, nmsNBTTagLongClass, long.class));
    private final Method createFloat = Objects
        .requireNonNull(JavaAccessor.getStaticMethod(nmsNBTTagFloatClass, nmsNBTTagFloatClass, float.class));
    private final Method createDouble = Objects
        .requireNonNull(JavaAccessor.getStaticMethod(nmsNBTTagDoubleClass, nmsNBTTagDoubleClass, double.class));
    private final Method createString = Objects
        .requireNonNull(JavaAccessor.getStaticMethod(nmsNBTTagStringClass, nmsNBTTagStringClass, String.class));
    private final Constructor<?> createByteArray = Objects
        .requireNonNull(JavaAccessor.getConstructor(nmsNBTTagByteArrayClass, byte[].class));
    private final Constructor<?> createIntArray = Objects.requireNonNull(JavaAccessor.getConstructor(nmsNBTTagIntArrayClass, int[].class));
    private final Constructor<?> createLongArray = Objects
        .requireNonNull(JavaAccessor.getConstructor(nmsNBTTagLongArrayClass, long[].class));
    private final Constructor<?> createList = Objects.requireNonNull(JavaAccessor.getConstructor(nmsNBTTagListClass));
    private final Constructor<?> createCompound = Objects.requireNonNull(JavaAccessor.getConstructor(nmsNBTTagCompoundClass));

    private final Method getByte = Objects
        .requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagByteClass, byte.class, Arrays.asList(getType.getName())));
    private final Method getShort = Objects.requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagShortClass, short.class));
    private final Method getInt = Objects.requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagIntClass, int.class));
    private final Method getLong = Objects.requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagLongClass, long.class));
    private final Method getFloat = Objects.requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagFloatClass, float.class));
    private final Method getDouble = Objects.requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagDoubleClass, double.class));
    private final Method getString = Objects
        .requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagStringClass, String.class, Arrays.asList("toString")));
    private final Method getByteArray = Objects.requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagByteArrayClass, byte[].class));
    private final Method getIntArray = Objects.requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagIntArrayClass, int[].class));
    private final Method getLongArray = Objects.requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagLongArrayClass, long[].class));

    private final Method compoundAddItem = Objects
        .requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagCompoundClass, nmsNBTBaseClass, String.class, nmsNBTBaseClass));
    private final Method compoundGetItem = Objects
        .requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagCompoundClass, nmsNBTBaseClass, String.class));
    private final Method compoundGetKeys = Objects.requireNonNull(JavaAccessor.getDeclaredMethod(nmsNBTTagCompoundClass, Set.class));

    private Object tagToMinecraft(NbtTag tag) {
        switch (tag.getType()) {
        case END:
            return JavaAccessor.getStaticValue(getEnd);
        case BYTE:
            return JavaAccessor.invokeStatic(createByte, ((NbtByte) tag).getByteValue());
        case SHORT:
            return JavaAccessor.invokeStatic(createShort, ((NbtShort) tag).getShortValue());
        case INT:
            return JavaAccessor.invokeStatic(createInt, ((NbtInt) tag).getIntValue());
        case LONG:
            return JavaAccessor.invokeStatic(createLong, ((NbtLong) tag).getLongValue());
        case FLOAT:
            return JavaAccessor.invokeStatic(createFloat, ((NbtFloat) tag).getFloatValue());
        case DOUBLE:
            return JavaAccessor.invokeStatic(createDouble, ((NbtDouble) tag).getDoubleValue());
        case STRING:
            return JavaAccessor.invokeStatic(createString, ((NbtString) tag).getValue());
        case BYTE_ARRAY:
            return JavaAccessor.instance(createByteArray, ((NbtByteArray) tag).getValue());
        case INT_ARRAY:
            return JavaAccessor.instance(createIntArray, ((NbtIntArray) tag).getValue());
        case LONG_ARRAY:
            return JavaAccessor.instance(createLongArray, ((NbtLongArray) tag).getValue());
        case LIST:
            return listToMinecraft((NbtList<?>) tag);
        case COMPOUND:
            return compoundToMinecraft((NbtCompound) tag);
        default:
            throw new IllegalArgumentException("Unsupported Type!");
        }
    }

    @SuppressWarnings({
        "rawtypes",
        "unchecked"
    })
    private List listToMinecraft(NbtList<?> list) {
        List minecraft = (List) JavaAccessor.instance(createList);
        for (NbtTag tag : list) {
            if (tag == null) {
                continue;
            }
            minecraft.add(tagToMinecraft(tag));
        }
        return minecraft;
    }

    private Object compoundToMinecraft(NbtCompound compound) {
        Object minecraft = JavaAccessor.instance(createCompound);
        for (String key : compound.getKeys()) {
            if (compound.get(key) == null) {
                continue;
            }
            JavaAccessor.invoke(minecraft, compoundAddItem, key, tagToMinecraft(compound.get(key)));
        }
        return minecraft;
    }

    @SuppressWarnings("rawtypes")
    private NbtTag tagFromMinecraft(Object minecraftTag) {
        switch (NbtType.getById((byte) JavaAccessor.invoke(minecraftTag, getType))) {
        case END:
            return NbtEnd.INSTANCE;
        case BYTE:
            return new NbtByte((byte) JavaAccessor.invoke(minecraftTag, getByte));
        case SHORT:
            return new NbtShort((short) JavaAccessor.invoke(minecraftTag, getShort));
        case INT:
            return new NbtInt((int) JavaAccessor.invoke(minecraftTag, getInt));
        case LONG:
            return new NbtLong((long) JavaAccessor.invoke(minecraftTag, getLong));
        case FLOAT:
            return new NbtFloat((float) JavaAccessor.invoke(minecraftTag, getFloat));
        case DOUBLE:
            return new NbtDouble((double) JavaAccessor.invoke(minecraftTag, getDouble));
        case STRING:
            return new NbtString((String) JavaAccessor.invoke(minecraftTag, getString));
        case BYTE_ARRAY:
            return new NbtByteArray((byte[]) JavaAccessor.invoke(minecraftTag, getByteArray));
        case INT_ARRAY:
            return new NbtIntArray((int[]) JavaAccessor.invoke(minecraftTag, getIntArray));
        case LONG_ARRAY:
            return new NbtLongArray((long[]) JavaAccessor.invoke(minecraftTag, getLongArray));
        case LIST:
            return listFromMinecraft((List) minecraftTag);
        case COMPOUND:
            return compoundFromMinecraft(minecraftTag);
        default:
            throw new IllegalArgumentException("Unsupported Type!");
        }
    }

    @SuppressWarnings("rawtypes")
    private NbtList<?> listFromMinecraft(List minecraftList) {
        NbtList<?> list = new NbtList<>();
        for (Object object : minecraftList) {
            if (object == null) {
                continue;
            }
            list.addTag(tagFromMinecraft(object));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private NbtCompound compoundFromMinecraft(Object minecraftCompound) {
        NbtCompound compound = new NbtCompound();
        Set<String> keys = (Set<String>) JavaAccessor.invoke(minecraftCompound, compoundGetKeys);
        for (String key : keys) {
            Object object = JavaAccessor.invoke(minecraftCompound, compoundGetItem, key);
            if (object == null) {
                continue;
            }
            compound.set(key, tagFromMinecraft(object));
        }
        return compound;
    }

}
