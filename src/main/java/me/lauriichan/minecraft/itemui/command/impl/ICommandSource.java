package me.lauriichan.minecraft.itemui.command.impl;

import com.syntaxphoenix.avinity.command.node.Root;

public interface ICommandSource {

    String name();

    Root<BukkitSource> build(String name);

}
