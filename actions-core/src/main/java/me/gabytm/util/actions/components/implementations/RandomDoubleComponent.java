package me.gabytm.util.actions.components.implementations;

import me.gabytm.util.actions.components.Component;
import me.gabytm.util.actions.placeholders.PlaceholderManager;
import me.gabytm.util.actions.utils.Numbers;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.SplittableRandom;

public class RandomDoubleComponent<T> extends Component<T, Double> {

    private static final SplittableRandom RANDOM = new SplittableRandom();
    public static final String ID = "random";

    private final String minString;
    private final String maxString;

    private final Double minDouble;
    private final Double maxDouble;

    private final int precision;

    public RandomDoubleComponent(@NotNull String stringValue, @NotNull PlaceholderManager placeholderManager) {
        super(stringValue, placeholderManager);
        final String[] parts = stringValue.split(",");

        if (parts.length == 3) {
            precision = Numbers.tryParse(parts[0], -1);
            minString = parts[1];
            maxString = parts[2];
        } else {
            precision = -1;
            minString = parts[0];
            maxString = parts[1];
        }

        minDouble = Numbers.tryParseDouble(minString);
        maxDouble = Numbers.tryParseDouble(maxString);
    }

    @Override
    public @NotNull Double parse(T t) {
        double result;

        // The values didn't contain placeholders or anything
        if (minDouble != null && maxDouble != null) {
            result = (minDouble.equals(maxDouble)) ? minDouble : RANDOM.nextDouble(minDouble, maxDouble + 1D);
        } else {
            // Replace the placeholders and attempt to parse the strings again
            final double min = Numbers.tryParse(placeholderManager.replace(t, minString), 0D);
            final double max = Numbers.tryParse(placeholderManager.replace(t, maxString), 0D);

            result = (min == max) ? min : RANDOM.nextDouble(min, max + 1D);
        }

        return (precision < 0) ? result : BigDecimal.valueOf(result).setScale(precision, RoundingMode.HALF_UP).doubleValue();
    }

}
