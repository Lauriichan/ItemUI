package me.lauriichan.minecraft.itemui.util;

import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public final class JavaHelper {

    private JavaHelper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String formatPascalCase(final Enum<?> type) {
        return formatPascalCase(type.name().replace('_', ' ').replace('$', '-'));
    }

    public static String formatPascalCase(final String string) {
        final String[] parts = string.split(" ");
        final StringBuilder builder = new StringBuilder();
        for (int index = 0; index < parts.length; index++) {
            String part = parts[index];
            String[] buildParts = part.contains("-") ? part.split("-")
                : new String[] {
                    part
                };
            if (index != 0) {
                builder.append(' ');
            }
            for (int idx = 0; idx < buildParts.length; idx++) {
                if ((idx + 1) != buildParts.length) {
                    builder.append('-');
                }
                builder.append(Strings.firstLetterToUpperCase(buildParts[idx]));
            }
        }
        return builder.toString();
    }

}
