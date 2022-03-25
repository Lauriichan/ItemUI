package me.lauriichan.minecraft.itemui.compatibility.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import me.lauriichan.minecraft.itemui.compatibility.IHeadProvider;

public final class HeadProvider1_18 implements IHeadProvider {

    public static final String URL_BASE = "http://textures.minecraft.net/texture/%s";

    private final ConcurrentHashMap<String, PlayerProfile> profiles = new ConcurrentHashMap<>();

    private PlayerProfile buildProfile(final String texture) {
        final PlayerProfile profile = Bukkit.createPlayerProfile(HEAD_ID, "LootHead");
        profile.getTextures().setSkin(buildUrl(texture));
        return profile;
    }

    private URL buildUrl(final String texture) {
        try {
            return new URL(String.format(URL_BASE, texture));
        } catch (final MalformedURLException e) {
            return null;
        }
    }

    @Override
    public void setTexture(final SkullMeta meta, final String texture) {
        meta.setOwnerProfile(profiles.computeIfAbsent(texture, this::buildProfile));
    }

    @Override
    public String getTexture(final SkullMeta meta) {
        final PlayerProfile profile = meta.getOwnerProfile();
        if (profile == null) {
            return null;
        }
        final URL url = profile.getTextures().getSkin();
        if (url == null) {
            return null;
        }
        final String[] parts = url.toString().split("/");
        return parts[parts.length - 1];
    }

}
