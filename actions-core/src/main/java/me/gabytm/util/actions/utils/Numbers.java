package me.gabytm.util.actions.utils;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
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

    public static int tryParse(@Nullable final String string, final int def) {
        if (string == null) {
            return def;
        }

        final Integer value = Ints.tryParse(string);
        return (value == null) ? def : value;
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
        final Double result = tryParseDouble(string);
        return (result == null) ? def : result;
    }

    @Nullable
    public static Double tryParseDouble(@Nullable final String string) {
        if (string == null) {
            return null;
        }

        return Doubles.tryParse(string);
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
        final Long result = tryParseLong(string);
        return (result == null) ? def : result;
    }

    @Nullable
    public static Long tryParseLong(@Nullable final String string) {
        if (string == null) {
            return null;
        }

        return Longs.tryParse(string);
    }

}
