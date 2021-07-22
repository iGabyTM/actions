package me.gabytm.util.actions.spigot;

import me.gabytm.util.actions.actions.ActionManager;
import me.gabytm.util.actions.tasks.TaskProcessor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpigotActionManager extends ActionManager {

    public SpigotActionManager(@Nullable TaskProcessor taskProcessor, double maxChance) {
        super(taskProcessor, maxChance);
    }

    public SpigotActionManager(@NotNull JavaPlugin plugin, double maxChance) {
        super(new SpigotTaskProcessor(plugin), maxChance);
    }

    public SpigotActionManager(@NotNull JavaPlugin plugin) {
        super(new SpigotTaskProcessor(plugin), 100D);
    }

}
