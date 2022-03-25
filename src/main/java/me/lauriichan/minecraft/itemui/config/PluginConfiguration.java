package me.lauriichan.minecraft.itemui.config;

import java.io.File;

public class PluginConfiguration extends BaseConfiguration {

    public PluginConfiguration(final File folder) {
        super(folder, "config.yml");
    }

    @Override
    protected void onReload() {
        DEBUG = get("debug", false);
    }

}
