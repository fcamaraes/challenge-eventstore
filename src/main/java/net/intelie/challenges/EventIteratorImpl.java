package net.intelie.challenges;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class EventIteratorImpl implements EventIterator {
    private Iterator<Event> eventIterator;
    private ConcurrentHashMap<String, Event> eventStore;
    private Event currentEvent;


    EventIteratorImpl(Iterator<Event> iterator, ConcurrentHashMap<String, Event> eventStore) {
        this.eventIterator = iterator;
        this.eventStore = eventStore;
    }

    @Override
    public boolean moveNext() {
        while (eventIterator.hasNext()) {
            currentEvent = eventIterator.next();
            return true;
        }
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
        eventStore.remove(currentEvent.id());
    }

    @Override
    public void close() throws Exception {

    }

    private void checkEvent() {
        if (currentEvent == null) {
            throw new IllegalStateException();
        }
    }
}
