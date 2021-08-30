package net.intelie.challenges;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class EventIteratorImpl implements EventIterator {
    private Iterator<Event> eventIterator;
    private ConcurrentHashMap<String, Event> eventStore;
    private Event currentEvent;
    private boolean moveNextCalledStatus = false;


    EventIteratorImpl(Iterator<Event> iterator, ConcurrentHashMap<String, Event> eventStore) {
        this.eventIterator = iterator;
        this.eventStore = eventStore;
    }

    @Override
    public boolean moveNext() {
        moveNextCalledStatus = true;

        while (eventIterator.hasNext()) {
            currentEvent = eventIterator.next();
            return true;
        }
        currentEvent = null;
        return false;
    }


    @Override
    public Event current() {
        checkEvent();
        return currentEvent;
    }

    @Override
    public void remove() {
        checkEvent();
        eventStore.remove(currentEvent);
    }

    @Override
    public void close() throws Exception {

    }

    private void checkEvent() {
        if (!moveNextCalledStatus || currentEvent == null) {
            throw new IllegalStateException();
        }
    }
}
