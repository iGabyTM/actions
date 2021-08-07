package me.gabytm.util.actions.actions;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import me.gabytm.util.actions.actions.implementations.DataAction;
import me.gabytm.util.actions.components.ComponentParser;
import me.gabytm.util.actions.placeholders.PlaceholderManager;
import me.gabytm.util.actions.tasks.DefaultTaskProcessor;
import me.gabytm.util.actions.tasks.TaskProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ActionManager {

    private final PlaceholderManager placeholderManager = new PlaceholderManager();
    private final ComponentParser componentParser = new ComponentParser(placeholderManager);
    private final ActionParser actionParser = new ActionParser(this);
    private final SplittableRandom random = new SplittableRandom();

    private final Table<String, Class<?>, Action.Supplier<?>> actions = HashBasedTable.create();

    private final TaskProcessor taskProcessor;
    private final double maxChance;

    public ActionManager(@Nullable TaskProcessor taskProcessor, double maxChance) {
        this.taskProcessor = (taskProcessor == null) ? new DefaultTaskProcessor() : taskProcessor;
        this.maxChance = maxChance + 1D;
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
    protected final <T> Action<T> createAction(@NotNull final Class<T> clazz, @NotNull final String id,
                                               @NotNull final ActionMeta<T> meta
    ) {
        final Action.Supplier<?> supplier = actions.get(id.toLowerCase(), clazz);

        if (supplier == null) {
            return null;
        }

        return ((Action.Supplier<T>) supplier).run(meta);
    }

    @NotNull
    public PlaceholderManager getPlaceholderManager() {
        return placeholderManager;
    }

    @NotNull
    public ComponentParser getComponentParser() {
        return componentParser;
    }

    public <T> boolean isRegistered(@NotNull final Class<T> clazz, @NotNull final String id) {
        return actions.get(id.toLowerCase(), clazz) != null;
    }

    /**
     * Register an action with the given id and type
     *
     * @param id     action id
     * @param clazz  action type (class)
     * @param action function to create a new instance
     * @param <T>    action type
     */
    public <T> void register(@NotNull final Class<T> clazz, @NotNull final String id,
                             @NotNull final Action.Supplier<T> action
    ) {
        actions.put(id.toLowerCase(), clazz, action);
    }

    public <T> void registerDefaults(@NotNull final Class<T> clazz) {
        register(clazz, DataAction.ID, DataAction::new);
    }

    /**
     * Parse a collection of strings into a {@link List} of {@code Action<T>}
     *
     * @param clazz   actions type (class)
     * @param actions list to parse
     * @param <T>     actions type
     * @return {@link List} of {@code Action<T>}
     */
    @NotNull
    public <T> List<Action<T>> parse(@NotNull final Class<T> clazz, @NotNull final Collection<String> actions) {
        return actions.stream()
                .map(it -> actionParser.parseAction(clazz, it))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Run the given actions with the given argument
     *
     * @param t       argument
     * @param actions actions to run
     * @param async   whether the actions should be run async or not
     * @param data    the default data (see {@link Context#getData(String)})
     * @param <T>     actions and argument type
     */
    public <T> void run(@NotNull final T t, @NotNull final List<Action<T>> actions,
                        final boolean async, @NotNull final Map<String, Object> data
    ) {
        final Context<T> context = new Context<>(actions, data);

        for (Action<T> action : context) {
            if (action.getMeta().hasChance()) {
                if (random.nextDouble(0D, maxChance) > action.getMeta().getChance()) {
                    continue;
                }
            }

            if (action.getMeta().hasDelay()) {
                if (async) {
                    taskProcessor.runAsync(() -> action.run(t, context), action.getMeta().getDelay());
                } else {
                    taskProcessor.runSync(() -> action.run(t, context), action.getMeta().getDelay());
                }

                continue;
            }

            if (async) {
                taskProcessor.runAsync(() -> action.run(t, context));
            } else {
                taskProcessor.runSync(() -> action.run(t, context));
            }
        }
    }

    /**
     * The equivalent of {@link #run(Object, List, boolean, Map)} called with {@link Collections#emptyMap()}
     * for {@code data}
     *
     * @param t       argument
     * @param actions actions to run
     * @param async   whether the actions should be run async or not
     * @param <T>     actions and argument type
     */
    public <T> void run(@NotNull final T t, @NotNull final List<Action<T>> actions, final boolean async) {
        run(t, actions, async, Collections.emptyMap());
    }

}
