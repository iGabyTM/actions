package me.gabytm.util.actions.utils;

import org.jetbrains.annotations.Nullable;

/**
 * Util class for interacting with numbers (eg. parse a string as double)
 * @author GabyTM
 * @since 1.0.0
 */
public final class Numbers {

    private Numbers() {
        throw new IllegalArgumentException();
    }

    /**
     * Parse the given string and return a {@code double} value otherwise {@code def} if
     * it couldn't be parsed
     *
     * @param string the string to parse
     * @param def    the default value
     * @return parsed value is succeeded, otherwise {@code def}
     * @since 1.0.0
     */
    public static double tryParse(@Nullable final String string, final double def) {
        if (string == null) {
            return def;
        }

        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException ignored) {
            return def;
        }
    }

    /**
     * Parse the given string and return a {@code long} value otherwise {@code def} if
     * it couldn't be parsed
     *
     * @param string the string to parse
     * @param def    the default value
     * @return parsed value is succeeded, otherwise {@code def}
     * @since 1.0.0
     */
    public static long tryParse(@Nullable final String string, final long def) {
        if (string == null) {
            return def;
        }

        try {
            return Long.parseLong(string);
        } catch (NumberFormatException ignored) {
            return def;
        }
    }

}
