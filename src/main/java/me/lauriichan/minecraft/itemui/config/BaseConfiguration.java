package me.lauriichan.minecraft.itemui.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;
import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

import me.lauriichan.minecraft.itemui.util.DynamicArray;
import me.lauriichan.minecraft.itemui.util.Tuple;

public abstract class BaseConfiguration extends Reloadable {

    private final String name;
    private final Tuple<String, Object> namePlaceholder;

    protected final YamlConfiguration configuration = new YamlConfiguration();

    private final DynamicArray<IConfigHandler> handlers = new DynamicArray<>();

    public BaseConfiguration(final File folder, final String name) {
        super(new File(folder, name));
        this.namePlaceholder = Tuple.of("name", name);
        this.name = name;
    }

    /*
     * Getter
     */

    public final String getName() {
        return name;
    }

    public final YamlConfiguration getConfiguration() {
        return configuration;
    }

    /*
     * Handler
     */

    public final BaseConfiguration register(final IConfigHandler handler) {
        if (handlers.contains(handler)) {
            return this;
        }
        handlers.add(handler);
        return this;
    }

    public final boolean unregister(final IConfigHandler handler) {
        return handlers.remove(handler);
    }

    public final void unregisterAll() {
        handlers.clear();
    }

    /*
     * Reload
     */

    @SuppressWarnings("unchecked")
    protected final void load() {
        if (file.exists()) {
            try {
                configuration.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                Messages.CONFIG_RELOAD_ERROR_LOAD.sendConsole(namePlaceholder);
                if (DEBUG) {
                    System.out.println(Exceptions.stackTraceToString(e));
                }
            }
        } else {
            clear();
        }
        final int length = handlers.length();
        for (int index = 0; index < length; index++) {
            final IConfigHandler handler = handlers.get(index);
            if (handler == null) {
                continue;
            }
            try {
                handler.onReload(this);
            } catch (final Throwable e) {
                Messages.CONFIG_RELOAD_HANDLER_FAILED.sendConsole(namePlaceholder);
                if (DEBUG) {
                    System.out.println(Exceptions.stackTraceToString(e));
                }
            }
        }
        try {
            onReload();
            Messages.CONFIG_RELOAD_SUCCESS.sendConsole(namePlaceholder);
        } catch (final Throwable e) {
            Messages.CONFIG_RELOAD_FAILED.sendConsole(namePlaceholder);
            if (DEBUG) {
                System.out.println(Exceptions.stackTraceToString(e));
            }
        }
        try {
            if (!file.exists()) {
                final File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                file.createNewFile();
            }
            configuration.save(file);
        } catch (final IOException e) {
            Messages.CONFIG_RELOAD_ERROR_SAVE.sendConsole(namePlaceholder);
            if (DEBUG) {
                System.out.println(Exceptions.stackTraceToString(e));
            }
        }
    }

    protected void onReload() {}

    /*
     * Configuration usage
     */

    public final void clear() {
        for (final String key : configuration.getKeys(false)) {
            configuration.set(key, null);
        }
    }

    public boolean has(final String path) {
        return configuration.contains(path);
    }

    public <E> E get(final String path, Class<E> type) {
        if (!configuration.contains(path)) {
            return null;
        }
        final Object object = configuration.get(path);
        if (!(type = Primitives.fromPrimitive(type)).isAssignableFrom(object.getClass())) {
            return null;
        }
        return type.cast(object);
    }

    @SuppressWarnings("unchecked")
    public <E> E get(final String path, final E fallback) {
        final E value = get(path, (Class<E>) fallback.getClass());
        if (value == null) {
            configuration.set(path, fallback);
            return fallback;
        }
        return value;
    }

    public Number get(final String path, final Number fallback) {
        final Number value = get(path, Number.class);
        if (value == null) {
            configuration.set(path, fallback);
            return fallback;
        }
        return value;
    }

    public void unset(final String path) {
        configuration.set(path, null);
    }

    public void set(final String path, final Object value) {
        configuration.set(path, value);
    }

}
