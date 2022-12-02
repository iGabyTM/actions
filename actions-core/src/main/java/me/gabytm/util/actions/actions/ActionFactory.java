package me.gabytm.util.actions.actions;

public interface ActionFactory<T, A extends Action<T>> {

    A create(final ActionMeta<T> meta);

}
