package me.gabytm.util.actions.actions;

import me.gabytm.util.actions.components.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionParser {

    // https://regex101.com/r/vS44ka/1
    private static final Pattern ACTION_PATTERN = Pattern.compile(
            "(?:\\{(?<properties>.+)}\\s?)?(?:\\[(?<id>\\w+)])\\s?(?<data>.*)?"
    );

    private final ActionManager actionManager;

    public ActionParser(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    @NotNull
    private Map<String, String> parseProperties(@NotNull final Matcher matcher) {
        final String propertiesGroup = matcher.group("properties");

        if (propertiesGroup == null || propertiesGroup.isEmpty()) {
            return Collections.emptyMap();
        }

        final Map<String, String> properties = new HashMap<>();

        for (String part : propertiesGroup.split(" ")) {
            final String[] property = part.split("=", 2);

            if (property.length != 2) {
                continue;
            }

            properties.put(property[0], property[1]);
        }

        return properties;
    }

    @Nullable
    public <T> Action<T> parseAction(@NotNull final Class<T> clazz, @NotNull final String input) {
        final Matcher matcher = ACTION_PATTERN.matcher(input);

        if (!matcher.find()) {
            return null;
        }

        final String id = matcher.group("id");

        if (!actionManager.isRegistered(clazz, id)) {
            return null;
        }

        final String data = matcher.group("data");
        final List<Component<T, ?>> nodes = (data == null) ? Collections.emptyList() : actionManager.getComponentParser().parseComponents(clazz, data);
        final ActionMeta<T> meta = new ActionMeta<>(parseProperties(matcher), data, nodes);
        return actionManager.createAction(clazz, id, meta);
    }

}
