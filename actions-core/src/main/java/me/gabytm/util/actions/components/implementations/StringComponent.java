package me.gabytm.util.actions.components.implementations;

import me.gabytm.util.actions.actions.Context;
import me.gabytm.util.actions.components.Component;
import me.gabytm.util.actions.placeholders.PlaceholderManager;
import org.jetbrains.annotations.NotNull;

public class StringComponent<T> extends Component<T, String> {

    public StringComponent(@NotNull String stringValue, @NotNull PlaceholderManager placeholderManager) {
        super(stringValue, placeholderManager);
    }

    @Override
    public @NotNull String parse(@NotNull final T t, @NotNull final Context<T> context) {
        return placeholderManager.replace(t, stringValue, context);
    }

}
