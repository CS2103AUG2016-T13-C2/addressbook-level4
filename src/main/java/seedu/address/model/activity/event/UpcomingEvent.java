package seedu.address.model.activity.event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
    
    public void initialize(Collection<Activity> activities) {
        ArrayList<Event> events = new ArrayList<>();
        
        for (Activity activity : activities) {
            if (activity.getClass().getSimpleName().equalsIgnoreCase("Event") && ((Event) activity).isNotStarted()) {
                eventQueue.add((Event) activity);
            }
        }
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
     * Returns a list of events that is upcoming.
     * @return a list of the events with start time closest to current time.
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
    
    public static void refresh() {
        while (!eventQueue.peek().isNotStarted()) {
            eventQueue.remove();
        }
    }
}
