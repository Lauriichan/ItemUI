package me.lauriichan.minecraft.itemui.util.properties;

import java.util.function.Function;

@SuppressWarnings("rawtypes")
class VoidProperty implements IProperty {

    private final String key;

    public VoidProperty(final String key) {
        this.key = key;
    }

    @Override
    public Object get() {
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Class getOwner() {
        return Void.class;
    }

    @Override
    public boolean isInstance(final Class sample) {
        return false;
    }

    @Override
    public IProperty cast(final Class sample) {
        return this;
    }

    @Override
    public IProperty map(final Function mapper) {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

}
