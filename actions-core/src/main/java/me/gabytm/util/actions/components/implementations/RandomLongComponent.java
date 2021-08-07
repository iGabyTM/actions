package me.gabytm.util.actions.components.implementations;

import me.gabytm.util.actions.actions.Context;
import me.gabytm.util.actions.components.Component;
import me.gabytm.util.actions.placeholders.PlaceholderManager;
import me.gabytm.util.actions.utils.Numbers;
import org.jetbrains.annotations.NotNull;

import java.util.SplittableRandom;

public class RandomLongComponent<T> extends Component<T, Long> {

    private static final SplittableRandom RANDOM = new SplittableRandom();
    public static final String ID = "randomL";

    private final String minString;
    private final String maxString;

    private final Long minLong;
    private final Long maxLong;

    public RandomLongComponent(@NotNull String stringValue, @NotNull PlaceholderManager placeholderManager) {
        super(stringValue, placeholderManager);
        final String[] parts = stringValue.split(",");

        minString = parts[0];
        maxString = parts[1];

        minLong = Numbers.tryParseLong(minString);
        maxLong = Numbers.tryParseLong(maxString);
    }

    @Override
    public @NotNull Long parse(@NotNull final T t, @NotNull final Context<T> context) {
        // The values didn't contain placeholders or anything
        if (minLong != null && maxLong != null) {
            return  (minLong.equals(maxLong)) ? minLong : RANDOM.nextLong(minLong, maxLong + 1L);
        }

        // Replace the placeholders and attempt to parse the strings again
        final long min = Numbers.tryParse(placeholderManager.replace(t, minString, context), 0L);
        final long max = Numbers.tryParse(placeholderManager.replace(t, maxString, context), 0L);

        return (min == max) ? min : RANDOM.nextLong(min, max + 1L);
    }

}
