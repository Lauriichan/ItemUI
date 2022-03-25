package me.lauriichan.minecraft.itemui.compatibility.impl;

import me.lauriichan.minecraft.itemui.compatibility.IVersionProvider;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

public final class VersionProvider1_16 implements IVersionProvider {

    @Override
    public HoverEvent createTextHover(BaseComponent[] components) {
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(components));
    }

}
