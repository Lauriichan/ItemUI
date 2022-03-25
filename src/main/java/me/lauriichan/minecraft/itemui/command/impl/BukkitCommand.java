package me.lauriichan.minecraft.itemui.command.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import com.syntaxphoenix.avinity.command.CommandContext;
import com.syntaxphoenix.avinity.command.CommandManager;
import com.syntaxphoenix.avinity.command.connection.AbstractConnection;
import com.syntaxphoenix.avinity.command.connection.ManagerConnection;
import com.syntaxphoenix.avinity.command.connection.NodeConnection;
import com.syntaxphoenix.avinity.command.node.Node;

import me.lauriichan.minecraft.itemui.ItemUI;
import me.lauriichan.minecraft.itemui.config.BaseConfiguration;
import me.lauriichan.minecraft.itemui.config.Messages;
import me.lauriichan.minecraft.itemui.util.Tuple;

public final class BukkitCommand implements TabExecutor {

    private final AbstractConnection<BukkitSource> connection;
    private final ItemUI plugin;

    public BukkitCommand(final ItemUI plugin, final ICommandSource source) {
        this(plugin, source.name(), new NodeConnection<>(source.build(source.name()).build()));
    }

    public BukkitCommand(final ItemUI plugin, final String name, final CommandManager<BukkitSource> commandManager) {
        this(plugin, name, new ManagerConnection<>(commandManager));
    }

    public BukkitCommand(final ItemUI plugin, final String name, final AbstractConnection<BukkitSource> connection) {
        this.plugin = plugin;
        this.connection = connection;
        final PluginCommand command = plugin.getCommand(name);
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command bukkitCommand, final String alias, final String[] args) {
        final CommandContext<BukkitSource> context = connection.parse(new BukkitSource(plugin, sender), args);
        if (!context.isPermitted()) {
            return Collections.emptyList();
        }
        final ArrayList<String> suggestions = new ArrayList<>();
        connection.suggest(suggestions, context);
        return suggestions;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onCommand(final CommandSender sender, final Command bukkitCommand, final String label, final String[] args) {
        final BukkitSource source = new BukkitSource(plugin, sender);
        final CommandContext<BukkitSource> context = connection.parse(source, args);
        if (!context.hasCommand()) {
            final Node<BukkitSource> node = context.getNode();
            if (node != null) {
                source.send(Messages.COMMAND_DOESNT_EXIST, Tuple.of("name", node.getName()));
                return false;
            }
            source.send(Messages.COMMAND_DOESNT_EXIST, Tuple.of("name", args[0]));
            return false;
        }
        if (!context.isPermitted()) {
            source.send(Messages.ACTION_NOT_PERMITTED, Tuple.of("permission", context.hasPermission().id()));
            return false;
        }
        try {
            context.getCommand().execute(context);
        } catch (final Throwable throwable) {
            source.send(Messages.COMMAND_EXECUTION_FAILED, Tuple.of("name", context.getNode().getName()));
            if (BaseConfiguration.DEBUG) {
                source.getPlugin().getLogger().log(Level.WARNING, "Couldn't run command '" + label + "'!", throwable);
            }
        }
        return false;
    }

}
