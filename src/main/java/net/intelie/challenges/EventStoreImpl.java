package net.intelie.challenges;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class EventStoreImpl implements EventStore {

    private ConcurrentHashMap<String, Event> eventStore = new ConcurrentHashMap<>();

    public EventStoreImpl() {

    }

    @Override
    public void insert(Event event) { eventStore.put(event.id(), event); }

    @Override
    public void removeAll(String type) {
        eventStore.values().removeIf(event -> event.type() == type);
    }

    public int getSize() {
        return eventStore.size();
    }

    public boolean contains(String key) {
        return eventStore.containsKey(key);
    }

    /**
     * I use the stream method to then be able to filter the
     * eventStore values, I also pass the eventStore itself,
     * so that I can use its remove method.
     */
    @Override
    public EventIteratorImpl query(String type, long startTime, long endTime) {
        Iterator<Event> iterator = eventStore
                .values()
                .stream()
                .filter(event -> event.type() == type
                        && startTime <= event.timestamp()
                        && event.timestamp() < endTime)
                .iterator();

        return new EventIteratorImpl(iterator, eventStore);
    }

}
