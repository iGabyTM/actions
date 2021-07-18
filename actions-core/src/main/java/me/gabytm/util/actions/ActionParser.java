package me.gabytm.util.actions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionParser {

    private static final Pattern ACTION_PATTERN = Pattern.compile(
            "(?:\\{(?<properties>.+)}\\s?)?(?:\\[(?<identifier>\\w+)])\\s?(?<data>.*)?"
    );

    private final ActionManager actionManager;

    public ActionParser(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    private Map<String, String> parseProperties(final Matcher matcher) {
        final String propertiesGroup = matcher.group("properties");

        if (propertiesGroup == null) {
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

    public <T> Action<T> parse(final String input, final Class<T> type) {
        final Matcher matcher = ACTION_PATTERN.matcher(input);

        if (!matcher.find()) {
            return null;
        }

        final String id = matcher.group("identifier");
        final Class<?> actionType = actionManager.getType(id);

        if (actionType == null) {
            System.out.println("Not registered");
            return null;
        }

        if (!actionType.isAssignableFrom(type)) {
            System.out.println("Expected " + actionType + " but got " + type);
            return null;
        }

        final ActionMeta meta = new ActionMeta(parseProperties(matcher), matcher.group("data"));
        return actionManager.parseAction(id, meta);
    }

}
