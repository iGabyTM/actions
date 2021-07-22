package me.gabytm.util.actions.components;

import me.gabytm.util.actions.placeholders.PlaceholderManager;
import org.jetbrains.annotations.NotNull;

public abstract class Component<T, R> {

    protected final String stringValue;
    protected final PlaceholderManager placeholderManager;

    public Component(@NotNull String stringValue, @NotNull PlaceholderManager placeholderManager) {
        this.stringValue = stringValue;
        this.placeholderManager = placeholderManager;
    }

    @NotNull
    public abstract R parse(T t);

    @FunctionalInterface
    public interface Supplier<T, R> {

        Component<T, R> run(@NotNull final String stringValue, @NotNull final PlaceholderManager placeholderManager);

    }

}
