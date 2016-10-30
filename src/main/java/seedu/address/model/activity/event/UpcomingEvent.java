package seedu.address.model.activity.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

import seedu.address.model.activity.Activity;

/**
 * Keeps a queue of upcoming events.
 * Events that are ongoing or over will not be added to the queue.
 */
public class UpcomingEvent {
    private static PriorityQueue<Event> eventQueue;
    
    public UpcomingEvent() {
        eventQueue = new PriorityQueue<>();
    }
    
    public UpcomingEvent(Collection<Activity> activities) {
        ArrayList<Event> events = new ArrayList<>();
        
        for (Activity activity : activities) {
            if (activity.getClass().getSimpleName().equalsIgnoreCase("Event") && ((Event) activity).isNotStarted()) {
                events.add((Event) activity);
            }
        }
        
        eventQueue = new PriorityQueue<>(events);
    }
    
    public boolean addEvent(Event newEvent) {
        if (newEvent.isNotStarted()) {
            return eventQueue.add(newEvent);
        } else {
            return false;
        }
    }
    
    public boolean removeEvent(Event event) {
        return eventQueue.remove(event);
    }
    
    /**
     * Returns a list of events that is happening at the
     * @return a list of the next events
     */
    public static ArrayList<Event> returnNextEvents() {
        ArrayList<Event> nextEvents = new ArrayList<>();
        
        Event nextEvent = eventQueue.poll();
        nextEvents.add(nextEvent);
        
        while (nextEvent.equals(eventQueue.peek())) {
            nextEvents.add(eventQueue.poll());
        }
        
        return nextEvents;
    }
}
