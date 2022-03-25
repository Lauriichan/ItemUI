package me.lauriichan.minecraft.itemui.compatibility.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.json.value.JsonString;

import me.lauriichan.minecraft.itemui.compatibility.IHeadProvider;
import me.lauriichan.minecraft.itemui.compatibility.VersionConstant;
import me.lauriichan.minecraft.itemui.util.JavaAccessor;

public final class HeadProvider1_17 implements IHeadProvider {

    public static final String TEXTURE_STRING_START = "{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/";
    public static final String TEXTURE_STRING_END = "\"}}}";

    private static final Class<?> CRAFT_META_SKULL = JavaAccessor.getClass(VersionConstant.craftClassPath("inventory.CraftMetaSkull"));
    private static final Field CRAFT_META_SKULL_PROFILE = JavaAccessor.getField(CRAFT_META_SKULL, "profile");

    private static final JsonParser JSON_PARSER = new JsonParser();

    private final ConcurrentHashMap<String, GameProfile> profiles = new ConcurrentHashMap<>();

    private GameProfile buildProfile(final String texture) {
        final GameProfile profile = new GameProfile(HEAD_ID, "LootHead");
        final PropertyMap properties = profile.getProperties();
        properties.removeAll("textures");
        properties.put("textures", new Property("textures",
            Base64.getEncoder().encodeToString((TEXTURE_STRING_START + texture + TEXTURE_STRING_END).getBytes(StandardCharsets.UTF_8))));
        return profile;
    }

    @Override
    public void setTexture(final SkullMeta meta, final String texture) {
        JavaAccessor.setObjectValue(meta, CRAFT_META_SKULL_PROFILE, profiles.computeIfAbsent(texture, this::buildProfile));
    }

    @Override
    public String getTexture(final SkullMeta meta) {
        final GameProfile profile = (GameProfile) JavaAccessor.getObjectValue(meta, CRAFT_META_SKULL_PROFILE);
        final Collection<Property> collection = profile.getProperties().get("textures");
        if (collection.isEmpty()) {
            return null;
        }
        JsonValue<?> json;
        try {
            json = JSON_PARSER
                .fromString(new String(Base64.getDecoder().decode(collection.iterator().next().getValue()), StandardCharsets.UTF_8));
        } catch (final IOException e) {
            return null;
        }
        if (!(json instanceof JsonObject)) {
            return null;
        }
        JsonObject object = (JsonObject) json;
        if (!object.has("textures", ValueType.OBJECT)) {
            return null;
        }
        object = (JsonObject) object.get("textures");
        if (!object.has("SKIN", ValueType.OBJECT)) {
            return null;
        }
        object = (JsonObject) object.get("SKIN");
        if (!object.has("url", ValueType.STRING)) {
            return null;
        }
        final String[] url = ((JsonString) object.get("url")).getValue().split("/");
        return url[url.length - 1];
    }

}
