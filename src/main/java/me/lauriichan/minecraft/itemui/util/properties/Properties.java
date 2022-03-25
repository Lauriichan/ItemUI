package me.lauriichan.minecraft.itemui.util.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Properties implements IProperties {

    private final List<IProperty<?>> properties = Collections.synchronizedList(new ArrayList<>());

    @Override
    public IProperties set(final IProperty<?>... properties) {
        for (final IProperty<?> property : properties) {
            set(property);
        }
        return this;
    }

    @Override
    public IProperties set(final IProperty<?> property) {
        remove(property.getKey());
        if (property.isPresent()) {
            properties.add(property);
        }
        return this;
    }

    @Override
    public IProperties add(final IProperty<?>... properties) {
        for (final IProperty<?> property : properties) {
            add(property);
        }
        return this;
    }

    @Override
    public IProperties add(final IProperty<?> property) {
        if (has(property.getKey())) {
            return this;
        }
        if (property.isPresent()) {
            properties.add(property);
        }
        return this;
    }

    @Override
    public IProperties delete(final String... keys) {
        for (final String key : keys) {
            delete(key);
        }
        return this;
    }

    @Override
    public IProperties delete(final String key) {
        final IProperty<?> property = find(key);
        if (property.isPresent()) {
            properties.remove(property);
        }
        return this;
    }

    @Override
    public List<IProperty<?>> remove(final String... keys) {
        final ArrayList<IProperty<?>> list = new ArrayList<>();
        for (final String key : keys) {
            final IProperty<?> property = remove(key);
            if (property.isPresent()) {
                list.add(property);
            }
        }
        return list;
    }

    @Override
    public IProperty<?> remove(final String key) {
        final IProperty<?> property = find(key);
        if (property.isPresent()) {
            properties.remove(property);
        }
        return property;
    }

    @Override
    public List<IProperty<?>> find(final String... keys) {
        final ArrayList<IProperty<?>> list = new ArrayList<>();
        for (final String key : keys) {
            final IProperty<?> property = find(key);
            if (property.isPresent()) {
                list.add(property);
            }
        }
        return list;
    }

    @Override
    public IProperty<?> find(final String key) {
        return properties.stream().filter(property -> property.getKey().equals(key)).findFirst().orElse(new VoidProperty(key));
    }

    @Override
    public IProperties clear() {
        properties.clear();
        return this;
    }

    @Override
    public boolean has(final String key) {
        return properties.stream().anyMatch(property -> property.getKey().equals(key));
    }

    @Override
    public int count() {
        return properties.size();
    }

    @Override
    public boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override
    public List<IProperty<?>> asList() {
        return new ArrayList<>(properties);
    }

    @Override
    public IProperty<?>[] asArray() {
        return properties.toArray(new IProperty<?>[0]);
    }

}
