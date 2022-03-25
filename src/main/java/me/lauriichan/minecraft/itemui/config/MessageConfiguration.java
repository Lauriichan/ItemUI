package me.lauriichan.minecraft.itemui.config;

import java.io.File;

public class MessageConfiguration extends BaseConfiguration {

    public MessageConfiguration(final File folder) {
        super(folder, "message.yml");
    }

    @Override
    protected void onReload() {
        final Message[] values = Message.values();
        for (final Message value : values) {
            value.setTranslation(get(value.getId(), value.getFallback()));
        }
    }

}
