package me.gabytm.util.actions.placeholders;

import org.jetbrains.annotations.NotNull;

public interface PlaceholderProvider<T> {

    @NotNull
    Class<T> getType();

    @NotNull
    String replace(@NotNull T t, @NotNull String input);

}
