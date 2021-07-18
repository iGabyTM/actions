package me.gabytm.util.actions;

import me.gabytm.util.actions.utils.Numbers;

import java.util.Map;

public class ActionMeta {

    private static final long DEFAULT_DELAY = 0L;
    private static final double DEFAULT_CHANCE = 0d;

    private final Map<String, String> properties;
    private final String data;

    private final long delay;
    private final double chance;

    public ActionMeta(Map<String, String> properties, String data) {
        this.properties = properties;
        this.data = data == null ? "" : data;

        this.delay = Numbers.tryParse(properties.get("delay"), DEFAULT_DELAY);
        this.chance = Numbers.tryParse(properties.get("chance"), DEFAULT_CHANCE);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getData() {
        return data;
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
