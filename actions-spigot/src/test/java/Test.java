import me.gabytm.util.actions.actions.Action;
import me.gabytm.util.actions.actions.ActionManager;
import me.gabytm.util.actions.actions.ActionMeta;
import me.gabytm.util.actions.actions.Context;
import me.gabytm.util.actions.spigot.actions.SpigotActionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        final ActionManager actionManager = new SpigotActionManager(JavaPlugin.getProvidingPlugin(TestPlugin.class));
        actionManager.register(Player.class, "console", ConsoleCommandAction::new);

        final List<Action<Player>> actions = actionManager.parse(
                Player.class,
                Arrays.asList(
                        "[console] say Hey there %player%!",
                        "{delay=60} [console] say Hey there %player%, you should see this message 3s later :D",
                        "{chance=10} [console] say Hey %player%, you are pretty lucky today to receive this message ;)"
                )
        );

        actionManager.run(Bukkit.getPlayer("GabyTM"), actions, false);
    }

    private static class TestPlugin extends JavaPlugin { }

    private static class ConsoleCommandAction extends Action<Player> {

        public ConsoleCommandAction(@NotNull ActionMeta<Player> meta) {
            super(meta);
        }

        @Override
        public void run(@NotNull final Player player, @NotNull final Context<Player> context) {
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    getMeta().getRawData().replace("%player%", player.getName())
            );
        }

    }

}
