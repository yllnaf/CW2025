package com.comp2042;

/**
 * Represents a movement event in the game.
 * Contains event type and source information.
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;

    /**
     * Constructor to create a movement event.
     *
     * @param eventType type of movement event
     * @param eventSource source of the event (user or system)
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Gets the event type.
     *
     * @return event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Gets the event source.
     *
     * @return event source
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
