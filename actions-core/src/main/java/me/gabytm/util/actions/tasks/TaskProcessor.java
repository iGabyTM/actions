package me.gabytm.util.actions.tasks;

import org.jetbrains.annotations.NotNull;

public interface TaskProcessor {

    /**
     * Run an action async
     * @param task action
     */
    void runAsync(@NotNull final Runnable task);

    /**
     * Run an action async with a delay
     * Depending on implementation, the delay can be in milliseconds, seconds etc.
     * @param task action
     * @param delay delay
     */
    void runAsync(@NotNull final Runnable task, final long delay);

    /**
     * Run an action sync
     * @param task action
     */
    void runSync(@NotNull final Runnable task);

    /**
     * Run an action sync with a delay
     * Depending on implementation, the delay can be in milliseconds, seconds etc.
     * @param task action
     * @param delay delay
     */
    void runSync(@NotNull final Runnable task, final long delay);

}
