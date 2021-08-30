package net.intelie.challenges;

/**
 * This is just an event stub, feel free to expand it if needed.
 */
public class Event {
    private final String id;
    private final String type;
    private final long timestamp;

    public Event(String id, String type, long timestamp) {
        this.type = type;
        this.id = id;
        this.timestamp = timestamp;
    }

    public String id() {
        return id;
    }

    public String type() {
        return type;
    }

    public long timestamp() {
        return timestamp;
    }

}
