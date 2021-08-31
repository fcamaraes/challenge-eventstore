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
        if (currentEvent == null) throw new IllegalStateException();
        return currentEvent;
    }

    @Override
    public void remove() {
        if (currentEvent == null) throw new IllegalStateException();
        eventStore.remove(currentEvent.id());
    }

    @Override
    public void close() throws Exception {

    }
}
