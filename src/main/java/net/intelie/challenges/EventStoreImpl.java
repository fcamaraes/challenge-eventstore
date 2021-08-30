package net.intelie.challenges;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class EventStoreImpl implements EventStore {

    private ConcurrentHashMap<String, Event> eventStore = new ConcurrentHashMap<>();

    public EventStoreImpl() {

    }

    @Override
    public void insert(Event event) {
        eventStore.put(event.id(), event);
    }

    @Override
    public void removeAll(String type) {
        eventStore.remove(type);
    }

    public int getSize() {
        return eventStore.size();
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        Iterator<Event> iterator = eventStore
                .values()
                .stream()
                .filter(event -> event.type() == type
                        && event.timestamp() >= startTime
                        && event.timestamp() <= endTime)
                .iterator();

        return new EventIteratorImpl(iterator, eventStore);
    }

}
