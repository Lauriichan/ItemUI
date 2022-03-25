package me.lauriichan.minecraft.itemui.config;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import me.lauriichan.minecraft.itemui.util.DynamicArray;
import me.lauriichan.minecraft.itemui.util.Reference;

public abstract class Reloadable {

    public static boolean DEBUG = false;

    private static final DynamicArray<Reloadable> RELOAD = new DynamicArray<>();
    private static final Reference<BukkitTask> TASK = Reference.of();

    protected final File file;
    private long lastUpdated = -1L;

    private final boolean saveOnExit;

    private boolean reload = true;

    public Reloadable(File file, boolean saveOnExit) {
        this.saveOnExit = saveOnExit;
        this.file = file;
        RELOAD.add(this);
    }

    public Reloadable(File file) {
        this(file, false);
    }

    public void setReloadEnabled(boolean reload) {
        if (this.reload == reload) {
            return;
        }
        this.reload = reload;
        if (reload) {
            RELOAD.add(this);
            return;
        }
        RELOAD.remove(this);
    }

    public boolean isReloadEnabled() {
        return reload;
    }

    public final File getFile() {
        return file;
    }

    protected void load() {}

    protected void save() {}

    public void forceUpdate() {
        lastUpdated = -1;
        update();
    }

    public void forceSave() {
        save();
        lastUpdated = file.lastModified();
    }

    public static void forceUpdateAll() {
        for (int index = 0; index < RELOAD.length(); index++) {
            RELOAD.get(index).forceUpdate();
        }

    }

    public static void update() {
        for (int index = 0; index < RELOAD.length(); index++) {
            final Reloadable reloadable = RELOAD.get(index);
            if (!reloadable.file.exists()) {
                reloadable.load();
                reloadable.forceSave();
                continue;
            }
            if (reloadable.lastUpdated == reloadable.file.lastModified()) {
                continue;
            }
            reloadable.load();
            reloadable.forceSave();
        }
    }

    public static void start(final Plugin plugin) {
        if (TASK.isPresent()) {
            return;
        }
        TASK.set(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, BaseConfiguration::update, 0, 100));
    }

    public static void shutdown() {
        if (TASK.isEmpty()) {
            return;
        }
        TASK.get().cancel();
        TASK.set(null);
        for (int index = 0; index < RELOAD.length(); index++) {
            final Reloadable reloadable = RELOAD.get(index);
            if (!reloadable.saveOnExit) {
                continue;
            }
            reloadable.save();
        }
    }

}
