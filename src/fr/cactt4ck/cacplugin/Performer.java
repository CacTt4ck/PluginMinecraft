package fr.cactt4ck.cacplugin;

@FunctionalInterface
public interface Performer<T> {

    public void perform(T value);

}
