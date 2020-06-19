package pt.tooyummytogo.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<E extends Event> {
    private List<Observer<E>> observers = new ArrayList<>();

    /**
     * Adds an Observer to the list of observers
     * @param o The Observer to add
     */
    public void addObserver(Observer<E> o) {
        observers.add(o);
    }

    /**
     * Dispatches an event to all the observers
     * @param e The Event to dispatch
     */
    protected void dispatchEvent(E e){
        for (Observer<E> o : observers) {
            o.handleNewEvent(e);
        }
    }
}
