package me.lauriichan.minecraft.itemui.config;

@FunctionalInterface
public interface IConfigHandler {

    void onReload(BaseConfiguration configuration);

}
