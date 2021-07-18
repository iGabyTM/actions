package me.gabytm.util.actions.spigot;

import me.gabytm.util.actions.tasks.TaskProcessor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SpigotTaskProcessor implements TaskProcessor {

    private final JavaPlugin plugin;

    public SpigotTaskProcessor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void runAsync(@NotNull Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    @Override
    public void runAsync(@NotNull Runnable task, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay);
    }

    @Override
    public void runSync(@NotNull Runnable task) {
        if (Bukkit.isPrimaryThread()) {
           task.run();
           return;
        }

        Bukkit.getScheduler().runTask(plugin, task);
    }

    @Override
    public void runSync(@NotNull Runnable task, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, task, delay);
    }

}
