package me.lauriichan.minecraft.itemui.compatibility;

import me.lauriichan.minecraft.itemui.compatibility.impl.VersionProvider1_13;
import me.lauriichan.minecraft.itemui.compatibility.impl.VersionProvider1_16;
import me.lauriichan.minecraft.itemui.util.Reference;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;

public interface IVersionProvider {

    Reference<IVersionProvider> PROVIDER = Reference.of();

    static IVersionProvider provider() {
        if (PROVIDER.isPresent()) {
            return PROVIDER.get();
        }
        try {
            Class.forName("net.md_5.bungee.api.chat.hover.content.Text");
            return PROVIDER.set(new VersionProvider1_16()).get();
        } catch (final ClassNotFoundException ignore) {
            return PROVIDER.set(new VersionProvider1_13()).get();
        }
    }

    HoverEvent createTextHover(BaseComponent[] components);

}
