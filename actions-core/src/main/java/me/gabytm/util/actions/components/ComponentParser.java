package me.gabytm.util.actions.components;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import me.gabytm.util.actions.components.implementations.MathComponent;
import me.gabytm.util.actions.components.implementations.RandomDoubleComponent;
import me.gabytm.util.actions.components.implementations.RandomLongComponent;
import me.gabytm.util.actions.components.implementations.StringComponent;
import me.gabytm.util.actions.placeholders.PlaceholderManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComponentParser {

    // https://regex101.com/r/vuTdBy/1
    private static final Pattern COMPONENT_PATTERN = Pattern.compile("~?(?<id>[a-zA-Z]+):\\{(?<data>[^}]+)}");

    private final Table<String, Class<?>, Component.Supplier<?, ?>> registeredComponents = HashBasedTable.create();

    private final PlaceholderManager placeholderManager;

    public ComponentParser(PlaceholderManager placeholderManager) {
        this.placeholderManager = placeholderManager;
    }

    @Nullable
    private <T> Component<T, ?> parseComponent(@NotNull final Class<T> clazz, @NotNull final String id,
                                               @NotNull final String value
    ) {
        final Component.Supplier<?, ?> supplier = registeredComponents.get(id.toLowerCase(), clazz);

        if (supplier == null) {
            return null;
        }

        return ((Component.Supplier<T, ?>) supplier).run(value, placeholderManager);
    }

    public <T, R> void register(@NotNull final Class<T> clazz, @NotNull final String id,
                                @NotNull final Component.Supplier<T, R> supplier
    ) {
        registeredComponents.put(id.toLowerCase(), clazz, supplier);
    }

    public <T> void registerDefaults(@NotNull final Class<T> clazz) {
        register(clazz, MathComponent.ID, MathComponent::new);
        register(clazz, RandomDoubleComponent.ID, RandomDoubleComponent::new);
        register(clazz, RandomLongComponent.ID, RandomLongComponent::new);
    }

    @NotNull
    public <T> List<Component<T, ?>> parse(@NotNull final Class<T> clazz, @NotNull final String string) {
        final Matcher matcher = COMPONENT_PATTERN.matcher(string);
        final List<Component<T, ?>> foundComponents = new ArrayList<>();

        int lastMatchEnd = 0;

        while (matcher.find()) {
            final int matchStart = matcher.start();
            final String id = matcher.group("id").toLowerCase();

            final Component<T, ?> node = parseComponent(clazz, id, matcher.group("data"));

            // A node syntax was found but a node with the provided class and id is not registered
            if (node == null) {
                foundComponents.add(new StringComponent<>(string.substring(lastMatchEnd, matcher.end()), placeholderManager));
                lastMatchEnd = matcher.end();
                continue;
            }

            // The match is not at the start of the string so the string that's before is be parsed as well
            if (matchStart != 0 && matchStart != lastMatchEnd) {
                foundComponents.add(new StringComponent<>(string.substring(lastMatchEnd, matchStart), placeholderManager));
            }

            foundComponents.add(node);
            lastMatchEnd = matcher.end();
        }

        // Adding the leftovers as StringNode
        if (lastMatchEnd != string.length()) {
            foundComponents.add(new StringComponent<>(string.substring(lastMatchEnd), placeholderManager));
        }

        return foundComponents;
    }

}
