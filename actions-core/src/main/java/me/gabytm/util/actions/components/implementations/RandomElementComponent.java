package me.gabytm.util.actions.components.implementations;

import me.gabytm.util.actions.actions.Context;
import me.gabytm.util.actions.components.Component;
import me.gabytm.util.actions.placeholders.PlaceholderManager;
import org.jetbrains.annotations.NotNull;

import java.util.SplittableRandom;

public class RandomElementComponent<T> extends Component<T, String> {

    private static final SplittableRandom RANDOM = new SplittableRandom();
    public static final String ID = "randomE";

    public RandomElementComponent(@NotNull String stringValue, @NotNull PlaceholderManager placeholderManager) {
        super(stringValue, placeholderManager);
    }

    @Override
    public @NotNull String parse(@NotNull T t, @NotNull Context<T> context) {
        final String[] elements = placeholderManager.replace(t, stringValue, context).split(",");
        final int length = elements.length;

        if (length == 1) {
            return elements[0];
        }

        return elements[RANDOM.nextInt(length)];
    }

}
