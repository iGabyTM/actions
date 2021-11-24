package me.gabytm.util.actions.actions;

import me.gabytm.util.actions.utils.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Context<T> implements Iterable<Action<T>> {

    private final Map<String, Object> data;
    private final List<Action<T>> actions;

    private final Map<String, String> stringData = new HashMap<>();

    public Context(List<Action<T>> actions, Map<String, Object> data) {
        this.actions = actions;
        this.data = data;
        data.forEach((key, value) -> stringData.put(key, String.valueOf(value)));
    }

    @NotNull
    @Override
    public Iterator<Action<T>> iterator() {
        return actions.iterator();
    }

    public void storeData(@NotNull final String key, @NotNull final Object value) {
        data.put(key, value);
        stringData.put(key, String.valueOf(value));
    }

    @Nullable
    public Object getData(@NotNull final String key) {
        return data.get(key);
    }

    @NotNull
    public String replaceData(@NotNull String input) {
        if (data.isEmpty() || input.trim().isEmpty()) {
            return input;
        }

        return Strings.replaceEach(input, stringData.keySet().toArray(new String[0]), stringData.values().toArray(new String[0]));
    }

}
