package me.gabytm.util.actions.spigot.actions;

import me.gabytm.util.actions.actions.ActionManager;
import me.gabytm.util.actions.spigot.tasks.SpigotTaskProcessor;
import me.gabytm.util.actions.spigot.placeholders.PlaceholderAPIProvider;
import me.gabytm.util.actions.tasks.TaskProcessor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpigotActionManager extends ActionManager {

    public SpigotActionManager(@Nullable TaskProcessor taskProcessor, double maxChance) {
        super(taskProcessor, maxChance);
        getPlaceholderManager().register(new PlaceholderAPIProvider());
    }

    public SpigotActionManager(@NotNull JavaPlugin plugin, double maxChance) {
        this(new SpigotTaskProcessor(plugin), maxChance);
    }

    public SpigotActionManager(@NotNull JavaPlugin plugin) {
        super(new SpigotTaskProcessor(plugin), 100D);
    }

}
