package me.lauriichan.minecraft.itemui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.lauriichan.minecraft.itemui.command.ItemUICommand;
import me.lauriichan.minecraft.itemui.command.impl.BukkitCommand;
import me.lauriichan.minecraft.itemui.config.MessageConfiguration;
import me.lauriichan.minecraft.itemui.config.Messages;
import me.lauriichan.minecraft.itemui.config.PluginConfiguration;
import me.lauriichan.minecraft.itemui.config.Reloadable;
import me.lauriichan.minecraft.itemui.config.item.SimpleItemContainer;
import me.lauriichan.minecraft.itemui.inventory.handle.GuiListener;
import me.lauriichan.minecraft.itemui.listener.JoinQuitListener;

public class ItemUI extends JavaPlugin {

    private SimpleItemContainer container;

    @Override
    public void onLoad() {
        Messages.PREFIX.getClass();
    }

    @Override
    public void onEnable() {
        registerConfigs();
        registerListeners();
        registerCommands();
        Reloadable.start(this);
    }

    @Override
    public void onDisable() {
        container.closeInventory();
        Reloadable.shutdown();
    }

    /*
     * Configs
     */

    private void registerConfigs() {
        new PluginConfiguration(getDataFolder());
        new MessageConfiguration(getDataFolder());
        container = new SimpleItemContainer(getDataFolder());
    }

    /*
     * Listeners
     */

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new GuiListener(this), this);
        pluginManager.registerEvents(new JoinQuitListener(), this);
    }

    /*
     * Commands
     */

    private void registerCommands() {
        new BukkitCommand(this, new ItemUICommand(container));
    }

}
