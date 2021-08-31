package net.intelie.challenges;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class Tests {

    public EventStoreImpl eventStore = new EventStoreImpl();

    @Test
    public void insertEvent() throws Exception {
        String key = "123456";
        Event event = new Event(key, "some_type", 123L);
        eventStore.insert(event);
        EventIterator iterator = eventStore.query("some_type", 120L, 123L);
        while (iterator.moveNext()) {
            eventStore.insert(event);
        }

        assertTrue(eventStore.contains(key));
    }

    @Test
    public void removeEvent() throws Exception {
        Event event = new Event("id", "some_type", 123L);
        eventStore.insert(event);
        EventIteratorImpl iterator = eventStore.query("some_type", 120L, 123L);
        while (iterator.moveNext()) {
            iterator.remove();
        }

        assertFalse(iterator.moveNext());
    }

    @Test
    public void removeAllEvent() throws Exception {
        for (Integer i = 0; i < 15; i++) {
            String stringId = i.toString();
            eventStore.insert(new Event(stringId, "A", i));
        }
        ;

        eventStore.removeAll("A");
        assertEquals(0, eventStore.getSize());
    }

    @Test
    public void queryCorrectly() throws Exception {
        for (Integer i = 0; i < 15; i++) {
            eventStore.insert(new Event(i.toString(), "A", i));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        EventIterator iterator = eventStore.query("A", 0, 15);
        int eventCount = 0;

        while (iterator.moveNext()) {
            eventCount++;
        }

        assertEquals(eventCount, eventStore.getSize());
    }

    @Test
    public void testThreadSafety() throws InterruptedException {

        Thread insertionThreadA1 = getInsertionThread("A", 0, 15);
        Thread insertionThreadA2 = getInsertionThread("A", 15, 30);
        Thread insertionThreadA3 = getInsertionThread("A", 5, 15);
        Thread insertionThreadA4 = getInsertionThread("A", 2, 18);
        Thread removalThread = getRemovalThread();


        insertionThreadA1.start();
        insertionThreadA2.start();
        insertionThreadA3.start();
        insertionThreadA4.start();
        //removalThread.start();
        insertionThreadA1.join();
        insertionThreadA2.join();
        insertionThreadA3.join();
        insertionThreadA4.join();
        //removalThread.join();

        EventIteratorImpl iteratorA = eventStore.query("A", 0, 30);
        int counter = 0;
        while (iteratorA.moveNext()) {
            counter++;
        }

        assertEquals(30, counter);
    }

    private Thread getRemovalThread() {
        Thread removalThread = new Thread(() -> {
            EventIteratorImpl iterator = eventStore.query("A", 0, 5);
            for (int i = 0; i < 5; i++) {
                iterator.moveNext();
                iterator.remove();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return removalThread;
    }

    private Thread getInsertionThread(String type, int begin, int end) {
        Thread insertionThread = new Thread(() -> {
            for (Integer i = begin; i < end; i++) {
                eventStore.insert(new Event(i.toString(), type, i));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return insertionThread;
    }

}