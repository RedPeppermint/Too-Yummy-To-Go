package pt.tooyummytogo.observer;

public interface Observer<E extends Event> {
    /**
     * Describes how this reacts to the Event
     * @param e The Event
     */
    void handleNewEvent(E e);
}
