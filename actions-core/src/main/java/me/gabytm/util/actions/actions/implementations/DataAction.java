package me.gabytm.util.actions.actions.implementations;

import me.gabytm.util.actions.actions.Action;
import me.gabytm.util.actions.actions.ActionMeta;
import me.gabytm.util.actions.actions.Context;
import org.jetbrains.annotations.NotNull;

public class DataAction<T> extends Action<T> {

    public static final String ID = "data";

    public DataAction(@NotNull ActionMeta<T> meta) {
        super(meta);
    }

    @Override
    protected boolean canRunAsync() {
        return true;
    }

    @Override
    public void run(@NotNull T t, @NotNull Context<T> context) {
        final String[] parts = meta.getParsedData(t, context).split(" ", 2);

        if (parts.length != 2) {
            return;
        }

        context.storeData(parts[0], parts[1]);
    }

}
