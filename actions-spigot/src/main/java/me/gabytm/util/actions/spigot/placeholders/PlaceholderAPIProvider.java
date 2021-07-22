package me.gabytm.util.actions.spigot.placeholders;

import me.clip.placeholderapi.PlaceholderAPI;
import me.gabytm.util.actions.placeholders.PlaceholderProvider;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIProvider implements PlaceholderProvider<OfflinePlayer> {

    @Override
    public @NotNull Class<OfflinePlayer> getType() {
        return OfflinePlayer.class;
    }

    @Override
    public @NotNull String replace(@NotNull OfflinePlayer player, @NotNull String input) {
        return PlaceholderAPI.setPlaceholders(player, input);
    }

}
