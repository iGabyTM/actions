package me.gabytm.util.actions.tasks;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DefaultTaskProcessor implements TaskProcessor {

    private final ExecutorService asyncService = Executors.newSingleThreadExecutor();
    private final ScheduledExecutorService asyncDelayedService = Executors.newSingleThreadScheduledExecutor();

    private final Timer syncTimer = new Timer();

    @Override
    public void runAsync(@NotNull Runnable task) {
        asyncService.submit(task);
    }

    @Override
    public void runAsync(@NotNull Runnable task, long delay) {
        asyncDelayedService.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runSync(@NotNull Runnable task) {
        task.run();
    }

    @Override
    public void runSync(@NotNull Runnable task, long delay) {
        syncTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay);
    }

}
