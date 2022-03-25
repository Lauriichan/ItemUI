package me.lauriichan.minecraft.itemui.compatibility;

import org.bukkit.Bukkit;

public final class VersionConstant {

    private VersionConstant() {
        throw new UnsupportedOperationException("Constant class");
    }

    public static final String CRAFTBUKKIT_PACKAGE = String.format("org.bukkit.craftbukkit.%s.%s",
        Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3], "%s");

    public static String craftClassPath(final String path) {
        return String.format(CRAFTBUKKIT_PACKAGE, path);
    }

}
