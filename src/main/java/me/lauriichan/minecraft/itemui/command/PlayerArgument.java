package me.lauriichan.minecraft.itemui.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.syntaxphoenix.avinity.command.StringReader;
import com.syntaxphoenix.avinity.command.type.IArgumentType;

public final class PlayerArgument implements IArgumentType<Player> {
    
    public static final IArgumentType<Player> PLAYER = new PlayerArgument();
    
    private PlayerArgument() {}

    @Override
    public Player parse(StringReader reader) throws IllegalArgumentException {
        String name = reader.read();
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            throw new IllegalArgumentException("The player '" + name + "' doesn't exist!");
        }
        return player;
    }

}
