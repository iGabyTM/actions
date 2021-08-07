package me.gabytm.util.actions.actions;

import me.gabytm.util.actions.components.Component;
import me.gabytm.util.actions.utils.Numbers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ActionMeta<T> {

    private static final long DEFAULT_DELAY = 0L;
    private static final double DEFAULT_CHANCE = 0D;

    private final Map<String, String> properties;
    private final String rawData;
    private final List<Component<T, ?>> components;

    private final long delay;
    private final double chance;

    public ActionMeta(@NotNull Map<String, String> properties, @Nullable String rawData,
                      @NotNull final List<Component<T, ?>> components
    ) {
        this.properties = properties;
        this.rawData = rawData == null ? "" : rawData;
        this.components = components;

        this.delay = Numbers.tryParse(properties.get("delay"), DEFAULT_DELAY);
        this.chance = Numbers.tryParse(properties.get("chance"), DEFAULT_CHANCE);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getRawData() {
        return rawData;
    }

    @NotNull
    public String getParsedData(@NotNull final T t, @NotNull final Context<T> context) {
        final StringBuilder stringBuilder = new StringBuilder(rawData.length());

        for (Component<T, ?> component : components) {
            stringBuilder.append(component.parse(t, context));
        }

        return stringBuilder.toString();
    }

    public long getDelay() {
        return delay;
    }

    public boolean hasDelay() {
        return getDelay() != DEFAULT_DELAY;
    }

    public double getChance() {
        return chance;
    }

    public boolean hasChance() {
        return getChance() != DEFAULT_CHANCE;
    }

}
