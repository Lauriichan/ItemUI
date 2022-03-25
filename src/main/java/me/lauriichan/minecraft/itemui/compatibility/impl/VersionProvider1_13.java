package me.lauriichan.minecraft.itemui.compatibility.impl;

import java.lang.reflect.Constructor;

import me.lauriichan.minecraft.itemui.compatibility.IVersionProvider;
import me.lauriichan.minecraft.itemui.util.JavaAccessor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;

public final class VersionProvider1_13 implements IVersionProvider {

    private static final Constructor<?> CONSTRUCT_HOVER = JavaAccessor.getConstructor(HoverEvent.class, HoverEvent.Action.class,
        BaseComponent[].class);

    @Override
    public HoverEvent createTextHover(BaseComponent[] components) {
        return (HoverEvent) JavaAccessor.instance(CONSTRUCT_HOVER, HoverEvent.Action.SHOW_TEXT, (Object) components);
    }

}
