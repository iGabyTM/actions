package me.gabytm.util.actions.actions;

import org.jetbrains.annotations.NotNull;

public abstract class Action<T> {

    private final ActionMeta<T> meta;

    public Action(@NotNull ActionMeta<T> meta) {
        this.meta = meta;
    }

    @NotNull
    public final ActionMeta<T> getMeta() {
        return meta;
    }

    public abstract void run(final T t);

    @FunctionalInterface
    public interface Supplier<T> {

        Action<T> run(ActionMeta<T> meta);

    }

}
