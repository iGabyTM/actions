package me.gabytm.util.actions.actions;

import com.google.common.base.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Context<T> implements Iterable<Action<T>> {

    private static final Pattern DATA_PATTERN = Pattern.compile("\\$\\[(?<key>[^]]+)]");

    private final Map<String, Object> data;
    private final List<Action<T>> actions;

    public Context(List<Action<T>> actions, Map<String, Object> data) {
        this.actions = actions;
        this.data = data;
    }

    @NotNull
    @Override
    public Iterator<Action<T>> iterator() {
        return actions.iterator();
    }

    public void storeData(@NotNull final String key, @NotNull final Object value) {
        data.put(key, value);
    }

    @Nullable
    public Object getData(@NotNull final String key) {
        return data.get(key);
    }

    @NotNull
    public String replaceData(@NotNull String input) {
        if (data.isEmpty()) {
            return input;
        }

        final Matcher matcher = DATA_PATTERN.matcher(input);

        while (matcher.find()) {
            input = input.replace(matcher.group(), String.valueOf(getData(matcher.group("key"))));
        }

        return input;
    }

}
