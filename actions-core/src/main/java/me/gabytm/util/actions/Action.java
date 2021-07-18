package me.gabytm.util.actions;

import org.jetbrains.annotations.NotNull;

public abstract class Action<T> {

    private final ActionMeta meta;

    public Action(@NotNull ActionMeta meta) {
        this.meta = meta;
    }

    @NotNull
    public final ActionMeta getMeta() {
        return meta;
    }

    public abstract void run(final T t);

}
