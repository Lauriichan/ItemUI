package me.lauriichan.minecraft.itemui.command.impl;

import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.syntaxphoenix.avinity.command.ISource;

import me.lauriichan.minecraft.itemui.ItemUI;
import me.lauriichan.minecraft.itemui.config.Message;
import me.lauriichan.minecraft.itemui.util.Tuple;

public final class BukkitSource implements ISource {

    private final CommandSender sender;
    private final ItemUI plugin;

    public BukkitSource(final ItemUI plugin, final CommandSender sender) {
        this.sender = sender;
        this.plugin = plugin;
    }

    public ItemUI getPlugin() {
        return plugin;
    }

    public CommandSender getSender() {
        return sender;
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public Player getPlayer() {
        return (Player) sender;
    }

    public Optional<Player> getPlayerAsOptional() {
        return Optional.of(sender).filter(sender -> sender instanceof Player).map(sender -> (Player) sender);
    }

    @SuppressWarnings("unchecked")
    public void send(final Message message) {
        message.send(sender);
    }

    @SuppressWarnings("unchecked")
    public void send(final Message message, final Tuple<String, Object>... placeholders) {
        message.send(sender, placeholders);
    }

    @Override
    public boolean hasPermission(final String id) {
        return sender.hasPermission(id);
    }

}
