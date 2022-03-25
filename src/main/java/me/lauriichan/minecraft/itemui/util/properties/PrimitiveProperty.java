package me.lauriichan.minecraft.itemui.util.properties;

import java.util.function.Function;

import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

@SuppressWarnings("unchecked")
class PrimitiveProperty<E> implements IProperty<E> {

    private final String key;
    private final E value;

    public PrimitiveProperty(final String key, final E value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public E get() {
        return value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Class<?> getOwner() {
        return value.getClass();
    }

    @Override
    public boolean isInstance(final Class<?> sample) {
        return Primitives.isInstance(sample)
            && (Primitives.fromPrimitive(sample).isInstance(value) || Primitives.toPrimitive(sample).isInstance(value));
    }

    @Override
    public <V> IProperty<V> cast(final Class<V> sample) {
        if (isInstance(sample)) {
            return (IProperty<V>) this;
        }
        return new VoidProperty(key);
    }

    @Override
    public <V> IProperty<V> map(final Function<E, V> mapper) {
        return IProperty.of(key, mapper.apply(value));
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}