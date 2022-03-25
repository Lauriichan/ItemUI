package me.lauriichan.minecraft.itemui.compatibility;

import java.util.UUID;

import org.bukkit.inventory.meta.SkullMeta;

import me.lauriichan.minecraft.itemui.compatibility.impl.HeadProvider1_17;
import me.lauriichan.minecraft.itemui.compatibility.impl.HeadProvider1_18;
import me.lauriichan.minecraft.itemui.util.Reference;

public interface IHeadProvider {

    UUID HEAD_ID = UUID.fromString("253b15c8-abf4-4e2c-af8b-69170d382e35");

    Reference<IHeadProvider> PROVIDER = Reference.of();

    static IHeadProvider provider() {
        if (PROVIDER.isPresent()) {
            return PROVIDER.get();
        }
        try {
            Class.forName("org.bukkit.profile.PlayerProfile");
            return PROVIDER.set(new HeadProvider1_18()).get();
        } catch (final ClassNotFoundException ignore) {
            return PROVIDER.set(new HeadProvider1_17()).get();
        }
    }

    void setTexture(SkullMeta meta, String texture);

    String getTexture(SkullMeta meta);

}
