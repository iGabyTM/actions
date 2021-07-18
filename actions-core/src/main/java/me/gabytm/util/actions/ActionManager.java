package me.gabytm.util.actions;

import me.gabytm.util.actions.tasks.DefaultTaskProcessor;
import me.gabytm.util.actions.tasks.TaskProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ActionManager {

    private final ActionParser actionParser = new ActionParser(this);
    private final SplittableRandom random = new SplittableRandom();

    private final Map<String, Class<?>> types = new HashMap<>();
    private final Map<String, Function<ActionMeta, Action<?>>> actions = new HashMap<>();

    private final TaskProcessor taskProcessor;
    private final double maxChance;

    public ActionManager(@Nullable TaskProcessor taskProcessor, double maxChance) {
        this.taskProcessor = (taskProcessor == null) ? new DefaultTaskProcessor() : taskProcessor;
        this.maxChance = maxChance + 1D;
    }

    /**
     * Get the type of an action by its ID
     *
     * @param id the id
     * @return type if an action with the provided id is registered, otherwise null
     */
    @Nullable
    protected Class<?> getType(@NotNull final String id) {
        return types.get(id.toLowerCase());
    }

    /**
     * Parse an action with the provided id and {@link ActionMeta}
     *
     * @param id   action id
     * @param meta action meta
     * @param <T>  action type
     * @return action if found, otherwise null
     */
    @Nullable
    protected <T> Action<T> parseAction(@NotNull final String id, @NotNull final ActionMeta meta) {
        final Function<ActionMeta, Action<?>> function = actions.get(id.toLowerCase());

        if (function == null) {
            return null;
        }

        return (Action<T>) function.apply(meta);
    }

    /**
     * Register an action with the given id and type
     *
     * @param id     action id
     * @param type   action type (class)
     * @param action function to create a new instance
     * @param <T>    action type
     */
    public <T> void register(@NotNull final String id, @NotNull final Class<T> type,
                             @NotNull final Function<ActionMeta, Action<?>> action
    ) {
        types.put(id.toLowerCase(), type);
        actions.put(id.toLowerCase(), action);
    }

    /**
     * Parse a collection of strings into a {@link List} of {@code Action<T>}
     *
     * @param tClass  actions type (class)
     * @param actions list to parse
     * @param <T>     actions type
     * @return {@link List} of {@code Action<T>}
     */
    @NotNull
    public <T> List<Action<T>> parse(@NotNull final Class<T> tClass, @NotNull final Collection<String> actions) {
        return actions.stream()
                .map(it -> actionParser.parse(it, tClass))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Run the given actions with the given argument
     * @param t argument
     * @param actions actions to run
     * @param async whether the actions should be run async or not
     * @param <T> actions and argument type
     */
    public <T> void run(@NotNull final T t, @NotNull final Collection<Action<T>> actions, final boolean async) {
        for (Action<T> action : actions) {
            if (action.getMeta().hasChance()) {
                if (random.nextDouble(0D, maxChance) > action.getMeta().getChance()) {
                    continue;
                }
            }

            if (action.getMeta().hasDelay()) {
                if (async) {
                    taskProcessor.runAsync(() -> action.run(t), action.getMeta().getDelay());
                } else {
                    taskProcessor.runSync(() -> action.run(t), action.getMeta().getDelay());
                }

                continue;
            }

            if (async) {
                taskProcessor.runAsync(() -> action.run(t));
            } else {
                taskProcessor.runSync(() -> action.run(t));
            }
        }
    }

}
