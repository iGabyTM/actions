package me.gabytm.util.actions.placeholders;

import me.gabytm.util.actions.actions.Context;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class PlaceholderManager {

    private final Set<PlaceholderProvider<?>> providers = new HashSet<>();

    public void register(@NotNull final PlaceholderProvider<?> provider) {
        providers.add(provider);
    }

    @NotNull
    public <T> String replace(@NotNull final T t, @NotNull final String string, @NotNull final Context<T> context) {
        return replace(t, context.replaceData(string));
    }

    @NotNull
    public <T> String replace(@NotNull final T t, @NotNull String string) {
        for (PlaceholderProvider<?> provider : providers) {
            if (provider.getType().isAssignableFrom(t.getClass())) {
                string = ((PlaceholderProvider<T>) provider).replace(t, string);
            }
        }

        return string;
    }

}
