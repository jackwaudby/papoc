package state;

import event.AbstractEvent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents the simulation's event list
 */
public class GlobalEventListSingleton {

    private int transactionsArrived = 0;

    private static final GlobalEventListSingleton instance = new GlobalEventListSingleton();
    private ArrayList<AbstractEvent> eventList;
    private GlobalEventListSingleton() {
        eventList = new ArrayList<>();
    }

    public static GlobalEventListSingleton getInstance() {
        return instance;
    }

    public void addEvent(AbstractEvent event) {
        eventList.add(event);
    }

    public void clearEvents() {
        eventList.clear();
    }

    public AbstractEvent getNextEvent() {
        Collections.sort(eventList);
        return eventList.remove(0);
    }

    public ArrayList<AbstractEvent> getEventList() {
        Collections.sort(eventList);
        return eventList;
    }

    public void incrementTransactionsArrived() {
        transactionsArrived = transactionsArrived + 1;
    }

    public int getTransactionsArrived() {
        return transactionsArrived;
    }
}
