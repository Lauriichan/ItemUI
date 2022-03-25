package me.lauriichan.minecraft.itemui.config;

import java.io.File;

import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;

import me.lauriichan.minecraft.itemui.util.Tuple;

public abstract class DataReloadable extends Reloadable {

    private final Tuple<String, Object> name;

    public DataReloadable(File file) {
        super(file);
        this.name = Tuple.of("name", file.getName());
    }

    public DataReloadable(File file, boolean saveOnExit) {
        super(file, saveOnExit);
        this.name = Tuple.of("name", file.getName());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected final void load() {
        Messages.DATA_LOAD_START.sendConsole(name);
        if (!file.exists()) {
            Messages.DATA_LOAD_SUCCESS.sendConsole(name);
            return;
        }
        try {
            onLoad();
            Messages.DATA_LOAD_SUCCESS.sendConsole(name);
        } catch (Throwable exp) {
            Messages.DATA_LOAD_FAILED.sendConsole(name);
            if (DEBUG) {
                System.err.println(Exceptions.stackTraceToString(exp));
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected final void save() {
        Messages.DATA_SAVE_START.sendConsole(name);
        try {
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            onSave();
            Messages.DATA_SAVE_SUCCESS.sendConsole(name);
        } catch (Throwable exp) {
            Messages.DATA_SAVE_FAILED.sendConsole(name);
            if (DEBUG) {
                System.err.println(Exceptions.stackTraceToString(exp));
            }
        }
    }

    protected void onLoad() throws Throwable {}

    protected void onSave() throws Throwable {}

}
